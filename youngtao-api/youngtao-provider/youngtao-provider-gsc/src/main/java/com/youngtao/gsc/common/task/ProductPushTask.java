package com.youngtao.gsc.common.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.mapper.SkuMapper;
import com.youngtao.gsc.model.domain.SkuDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    /** 6 - 次日0点，2小时递增 = (24-6) / 2 + 1 */
    private static final Integer REGION = 10;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private SkuMapper skuMapper;

    @Scheduled(cron = "0/30 * * * * ?")
    public void loadSkuToRedis() {
        // 每天5点执行，拉取6-次日0点的商品
        QueryWrapper<SkuDO> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.eq("is_marketable", 1);
        wrapper.gt("residue", 0);
        LocalDateTime startTime = LocalDateTime.now().withHour(6).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2);
        for (int i = 0; i < REGION; i++) {
            wrapper.le("start_time", startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            wrapper.ge("end_time", endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            List<SkuDO> skuDOList = skuMapper.selectList(wrapper);
            // 添加至redis
            String menu = DateUtils.formatToMenu(startTime);
            for (SkuDO skuDO : skuDOList) {
                redisTemplate.boundHashOps(RedisKey.SKU_SPACE.format(menu)).put(skuDO.getSkuId(), skuDO);
                redisTemplate.boundHashOps(RedisKey.SKU_SPACE.format(menu)).expire(24, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(skuDO.getSkuId(), skuDO.getResidue(), 24, TimeUnit.DAYS);
            }
            startTime = endTime;
            endTime = startTime.plusHours(2);
        }
    }
}
