package com.youngtao.gpc.common.task;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gpc.common.constant.RedisKey;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.core.util.ProductUtils;
import com.youngtao.gpc.mapper.SkuMapper;
import com.youngtao.gpc.model.convert.SkuConvert;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.gpc.model.domain.SkuDO;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Slf4j
@Component
public class ProductPushTask {
    @Autowired
    private RedisManager<String> redisManager;
    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private SkuConvert skuConvert;

    @Autowired
    private SpuFeign spuFeign;

    private final Cache<String, SpuDTO> spuCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(200)
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build();

    /**
     * 每天5-23点，每隔两小时加载下一个不可见菜单秒杀商品
     * 既：now + 1 + 2 * MENU_SIZE
     */
    //@Scheduled(cron = "0 0 5-23/2 * * ?")
    public void loadSkuToRedis() {
        int span = 1 + DateUtils.MENU_SIZE << 1;
        LocalDateTime startTime = LocalDateTime.now().plusHours(span).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2);
        String menu = DateUtils.formatToMenu(startTime);

        Set<SkuData> values = redisManager.zvalues(RedisKey.SKU_SET_KEY.format(DateUtils.currentMenu()));
        Set<String> idSet = values.stream().map(SkuData::getSkuId).collect(Collectors.toSet());
        List<SkuDO> skuDOList = skuMapper.loadSkuToRedis(Date.from(startTime.toInstant(ZoneOffset.of("+8"))),Date.from(endTime.toInstant(ZoneOffset.of("+8"))), idSet);
        if (CollectionUtils.isEmpty(skuDOList)) {
            return;
        }

        // 添加至redis
        Set<SkuData> skuDataList = skuDOList.stream().map(val -> {
            SkuData skuData = skuConvert.toSkuData(val);
            SpuDTO spuDTO = spuCache.get(val.getSpuId(), k -> {
                RpcResult<SpuDTO> spuResult = spuFeign.getBySpuId(val.getSpuId());
                return spuResult.getData();
            });
            if (spuDTO == null) {
                log.warn("spu does not exist, spuId = {}", val.getSpuId());
            } else {
                skuData.setTitle(ProductUtils.generateTitle(spuDTO.getSpu(), val.getSku()));
            }
            return skuData;
        }).collect(Collectors.toSet());
        // 添加至zset
        redisManager.zaddAll(RedisKey.SKU_SET_KEY.format(menu), skuDataList, span+3, TimeUnit.HOURS);
        // 添加库存
        for (SkuDO skuDO : skuDOList) {
            redisManager.setNum(RedisKey.SKU_COUNT_KEY.format(menu, skuDO.getSkuId()), skuDO.getResidue(), span+3, TimeUnit.HOURS);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        List<String> dateMenus = DateUtils.getDateMenus();
        for (int i = 0; i < dateMenus.size() - 1; i++) {
            int span = 1 + DateUtils.MENU_SIZE << 1;

            Date startTime = DateUtils.menuToDate(dateMenus.get(i));
            Date endTime = DateUtils.menuToDate(dateMenus.get(i+1));
            Set<SkuData> values = redisManager.zvalues(RedisKey.SKU_SET_KEY.format(dateMenus.get(i)));
            Set<String> idSet = values.stream().map(SkuData::getSkuId).collect(Collectors.toSet());
            List<SkuDO> skuDOList = skuMapper.loadSkuToRedis(startTime, endTime, idSet);
            if (CollectionUtils.isEmpty(skuDOList)) {
                continue;
            }

            // 添加至redis
            Set<SkuData> skuDataList = skuDOList.stream().map(val -> {
                SkuData skuData = skuConvert.toSkuData(val);
                SpuDTO spuDTO = spuCache.get(val.getSpuId(), k -> {
                    RpcResult<SpuDTO> spuResult = spuFeign.getBySpuId(val.getSpuId());
                    return spuResult.getData();
                });
                if (spuDTO == null) {
                    log.warn("spu does not exist, spuId = {}", val.getSpuId());
                } else {
                    skuData.setTitle(ProductUtils.generateTitle(spuDTO.getSpu(), val.getSku()));
                }
                return skuData;
            }).collect(Collectors.toSet());
            // 添加至zset
            redisManager.zaddAll(RedisKey.SKU_SET_KEY.format(dateMenus.get(i)), skuDataList, span+3, TimeUnit.HOURS);
            // 添加库存
            for (SkuData skuData : skuDataList) {
                redisManager.set(RedisKey.SKU_INFO_KEY.format(dateMenus.get(i), skuData.getSkuId()), skuData, span+3, TimeUnit.HOURS);
                redisManager.setNum(RedisKey.SKU_COUNT_KEY.format(dateMenus.get(i), skuData.getSkuId()), skuData.getResidue(), span+3, TimeUnit.HOURS);
            }
        }

    }
}
