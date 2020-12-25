package com.youngtao.gsc.common.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.gsc.common.cache.RedisManager;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.mapper.SkuMapper;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.domain.SkuDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Slf4j
@Component
public class ProductPushTask {
    @Autowired
    private RedisManager redisManager;
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
        for (SkuDO skuDO : skuDOList) {
            redisManager.put(RedisKey.SKU_SPACE.format(menu), RedisKey.SKU_KEY.format(skuDO.getSkuId()), skuConvert.toSkuData(skuDO), span+3, TimeUnit.HOURS);
            redisManager.set(RedisKey.SKU_COUNT_KEY.format(skuDO.getSkuId()), skuDO.getResidue(), span+3, TimeUnit.HOURS);
        }
    }
}
