package com.youngtao.gsc.service.impl;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.common.constant.CacheKey;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.manager.ProductManager;
import com.youngtao.gsc.model.convert.ProductConvert;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.request.ConfirmOrderRequest;
import com.youngtao.gsc.model.response.ConfirmOrderResponse;
import com.youngtao.gsc.model.response.GetSeckillPageResponse;
import com.youngtao.gsc.service.ProductService;
import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private DCacheManager<String> dCacheManager;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductConvert productConvert;
    @Autowired
    private SkuConvert skuConvert;

    @Override
    public GetSeckillPageResponse getSeckillPage() {
        GetSeckillPageResponse response = new GetSeckillPageResponse();
        response.setCurrentTime(new Date());
        Set<SkuData> skuDataSet = listByTime(DateUtils.currentMenu(), 1, 10);
        response.setSkuList(skuDataSet);
        return response;
    }

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

    @Override
    public ConfirmOrderResponse confirmOrder(ConfirmOrderRequest request) {
        String menu = DateUtils.currentMenu();
        String skuId = request.getSkuId();
        SpuDTO spuDTO = dCacheManager.get(CacheKey.SPU_KEY.format(menu, skuId), v -> {
            RpcResult<SpuDTO> spuDTOResult = spuFeign.getBySkuId(skuId);
            RpcResultUtils.checkNotNull(spuDTOResult);
            return spuDTOResult.getData();
        }, true);

        ConfirmOrderResponse response = new ConfirmOrderResponse();
        response.setMerchantId(spuDTO.getMerchantId());
        response.setShopName(spuDTO.getShopName());
        response.setPostage(BigDecimal.ZERO);
        // 获取sku
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        ConfirmOrderResponse.Sku sku = new ConfirmOrderResponse.Sku();
        sku.setSkuId(skuId);
        sku.setSpu(spuDTO.getSpu());
        sku.setSku(skuData.getSku());
        sku.setPrice(skuData.getPrice());
        sku.setImage(skuData.getImage());
        sku.setServe(spuDTO.getServe());
        sku.setCount(1);
        response.setSkuList(Lists.newArrayList(sku));
        return response;
    }

}
