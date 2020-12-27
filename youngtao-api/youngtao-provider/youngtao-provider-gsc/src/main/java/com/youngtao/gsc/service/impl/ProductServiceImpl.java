package com.youngtao.gsc.service.impl;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.gsc.common.constant.CacheKey;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.manager.ProductManager;
import com.youngtao.gsc.model.convert.ProductConvert;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.service.ProductService;
import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private DCacheManager<String> dCacheManager;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductConvert productConvert;
    @Autowired
    private SkuConvert skuConvert;

    @Override
    public Set<SkuData> listByTime(String time, Integer page, Integer size) {
        long start = (page - 1) * size;
        long end = page * size;
        return redisManager.zrange(RedisKey.SKU_SET_KEY.format(time), start, end);
    }

    @Override
    public ProductData detail(String time, String skuId) {
        Integer count = redisManager.getNum(RedisKey.SKU_COUNT_KEY.format(time, skuId));
        if (count == null) {
            return null;
        }
        ProductData data = dCacheManager.get(CacheKey.PRODUCT_KEY.format(time, skuId), v -> {
            SkuData skuData = productManager.getSkuData(time, skuId);
            RpcResult<ProductDTO> productResult = productFeign.getBySpuId(skuData.getSkuId());
            RpcResultUtils.checkNotNull(productResult);
            ProductData productData = productConvert.toProductData(productResult.getData());
            productData.getSkuList().removeIf(sku -> sku.getSkuId().equals(skuId));
            productData.getSkuList().add(skuConvert.toProductSku(skuData));
            return productData;
        }, true);
        for (ProductData.Sku sku : data.getSkuList()) {
            if (sku.getSkuId().equals(skuId)) {
                sku.setResidue(count);
            }
        }
        return data;
    }
}
