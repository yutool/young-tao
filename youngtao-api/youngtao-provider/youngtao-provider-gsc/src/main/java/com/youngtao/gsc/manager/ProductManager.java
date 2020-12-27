package com.youngtao.gsc.manager;

import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.mapper.SkuMapper;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.domain.SkuDO;
import com.youngtao.web.cache.DCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Service
public class ProductManager {

    @Autowired
    private DCacheManager<String> dCacheManager;
    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private SkuConvert skuConvert;

    public SkuData getSkuData(String menu, String skuId) {
        return dCacheManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId), k -> {
            SkuDO skuDO = skuMapper.getBySkuId(skuId);
            return skuConvert.toSkuData(skuDO);
        });
    }
}
