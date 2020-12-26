package com.youngtao.gsc.common.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.mapper.SkuMapper;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.domain.SkuDO;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    /**
     * 每天5-23点，每隔两小时加载下一个不可见菜单秒杀商品
     * 既：now + 1 + 2 * MENU_SIZE
     */
    @Scheduled(cron = "0 0 5-23/2 * * ?")
    public void loadSkuToRedis() {
        int span = 1 + DateUtils.MENU_SIZE << 1;
        LocalDateTime startTime = LocalDateTime.now().plusHours(span).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2);

        QueryWrapper<SkuDO> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.eq("is_marketable", 1);
        wrapper.gt("residue", 0);
        wrapper.le("start_time", startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        wrapper.ge("end_time", endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());

        List<SkuDO> skuDOList = skuMapper.selectList(wrapper);
        // 添加至redis
        String menu = DateUtils.formatToMenu(startTime);
        Set<SkuData> skuDataList = skuDOList.stream().map(val -> skuConvert.toSkuData(val)).collect(Collectors.toSet());
        redisManager.zaddAll(RedisKey.SKU_SET_KEY.format(menu), skuDataList, span+3, TimeUnit.HOURS);
        for (SkuDO skuDO : skuDOList) {
            redisManager.set(RedisKey.SKU_COUNT_KEY.format(menu, skuDO.getSkuId()), skuDO.getResidue(), span+3, TimeUnit.HOURS);
        }
    }
}
