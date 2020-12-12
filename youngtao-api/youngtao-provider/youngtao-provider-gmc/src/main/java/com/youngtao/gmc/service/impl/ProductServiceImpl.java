package com.youngtao.gmc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.lang.JsonList;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.gmc.common.constant.SpuConstant;
import com.youngtao.gmc.common.util.IdUtils;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.mapper.SpuMapper;
import com.youngtao.gmc.model.convert.ProductConvert;
import com.youngtao.gmc.model.convert.SkuConvert;
import com.youngtao.gmc.model.convert.SpuConvert;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private ProductConvert productConvert;
    @Autowired
    private SpuConvert spuConvert;
    @Autowired
    private SkuConvert skuConvert;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addProduct(AddProductRequest request) {
        String spuId = IdUtils.productId();
        // fill sku
        List<SkuDO> skuDOList = skuConvert.toSku(request.getSkuList());
        BigDecimal minPrice = BigDecimals.MAX_INT_VALUE;
        BigDecimal maxPrice = BigDecimal.ZERO;
        for (SkuDO skuDO : skuDOList) {
            skuDO.setSpuId(spuId);
            skuDO.setSkuId(IdUtils.productId());
            minPrice = minPrice.min(skuDO.getPrice());
            maxPrice = maxPrice.max(skuDO.getPrice());
        }
        // fill spu
        SpuDO spuDO = spuConvert.toSpu(request);
        spuDO.setSpuId(spuId);
        spuDO.setMerchantId("0");
        spuDO.setShopName("樱桃官方旗舰店");
        spuDO.setPriceRange(JsonList.build(minPrice, maxPrice));
        spuDO.setStatus(SpuConstant.REVIEW_SUCCESS);
        // save the data
        spuMapper.insert(spuDO);
        skuMapper.batchInset(skuDOList);
        return true;
    }

    @Override
    public ProductData getBySpuId(String id) {
        SpuDO spuDO = spuMapper.selectBySpuId(id);
        List<SkuDO> skuDOList = skuMapper.listBySpuId(id);
        return productConvert.toProductData(spuDO, skuDOList);
    }

    @Override
    public List<ConfirmOrderResponse> confirmOrder(ConfirmOrderRequest request) {
        Map<String, Integer> countMap = request.getSkuList().stream().collect(Collectors.toMap(ConfirmOrderRequest.Data::getSkuId, ConfirmOrderRequest.Data::getCount));
        List<SkuDO> skuDOS = skuMapper.listBySkuIds(countMap.keySet());

        Map<String, List<SkuDO>> skuDOMap = Maps.newHashMap();
        for (SkuDO skuDO : skuDOS) {
            List<SkuDO> list = skuDOMap.getOrDefault(skuDO.getSpuId(), Lists.newArrayList());
            list.add(skuDO);
            skuDOMap.put(skuDO.getSpuId(), list);
        }
        List<SpuDO> spuDOS = spuMapper.listBySpuIds(skuDOMap.keySet());

        // Processing return value
        Map<String, ConfirmOrderResponse> responseMap = Maps.newHashMap();
        for (SpuDO spuDO : spuDOS) {
            ConfirmOrderResponse response;
            if (responseMap.containsKey(spuDO.getMerchantId())) {
                response = responseMap.get(spuDO.getMerchantId());
                response.setPostage(response.getPostage().min(spuDO.getPostage()));
            } else {
                response = new ConfirmOrderResponse();
                response.setMerchantId(spuDO.getMerchantId());
                response.setShopName(spuDO.getShopName());
                response.setPostage(spuDO.getPostage());
                response.setSkuList(Lists.newArrayList());
            }
            for (SkuDO skuDO : skuDOMap.get(spuDO.getSpuId())) {
                ConfirmOrderResponse.Sku sku = new ConfirmOrderResponse.Sku();
                sku.setSkuId(skuDO.getSkuId());
                sku.setSku(skuDO.getSku());
                sku.setPrice(skuDO.getPrice());
                sku.setCount(countMap.get(skuDO.getSkuId()));
                sku.setServe(spuDO.getServe());
                response.getSkuList().add(sku);
            }
            responseMap.put(spuDO.getMerchantId(), response);
        }
        return Lists.newArrayList(responseMap.values());
    }
}
