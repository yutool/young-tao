package com.youngtao.gsc.manager;

import com.youngtao.gsc.common.cache.RedisManager;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.model.data.SkuData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Component
public class ProductManager {

    @Autowired
    private RedisManager redisManager;

    public List<SkuData> listByTime(String time) {
        List<SkuData> values = redisManager.hashValues(RedisKey.SKU_SPACE.format(time));
        return values;
    }

}
