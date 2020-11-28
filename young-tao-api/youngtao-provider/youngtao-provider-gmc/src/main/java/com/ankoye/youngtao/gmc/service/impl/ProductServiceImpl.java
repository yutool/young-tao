package com.ankoye.youngtao.gmc.service.impl;

import com.ankoye.youngtao.core.lang.JsonList;
import com.ankoye.youngtao.core.util.BigDecimals;
import com.ankoye.youngtao.core.util.GlobalIdUtils;
import com.ankoye.youngtao.gmc.common.constant.SpuConstant;
import com.ankoye.youngtao.gmc.mapper.SkuMapper;
import com.ankoye.youngtao.gmc.mapper.SpuMapper;
import com.ankoye.youngtao.gmc.model.data.ProductData;
import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.ankoye.youngtao.gmc.model.request.AddProductRequest;
import com.ankoye.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.ankoye.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.ankoye.youngtao.gmc.service.ProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addProduct(AddProductRequest request) {
        String spuId = GlobalIdUtils.generateId();
        // fill sku
        List<SkuDO> skuDOList = request.convertToSkuList();
        BigDecimal minPrice = BigDecimals.MAX_INT_VALUE;
        BigDecimal maxPrice = BigDecimal.ZERO;
        for (SkuDO skuDO : skuDOList) {
            skuDO.setSpuId(spuId);
            skuDO.setSkuId(GlobalIdUtils.generateId());
            minPrice = minPrice.min(skuDO.getPrice());
            maxPrice = maxPrice.max(skuDO.getPrice());
        }
        // fill spu
        SpuDO spuDO = request.convertToSpu();
        spuDO.setSpuId(spuId);
        spuDO.setMerchantId("0");
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
        return ProductData.copyBy(spuDO, skuDOList);
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

        List<ProductData> dataList = Lists.newArrayList();
        for (SpuDO spuDO : spuDOS) {
            ProductData data = ProductData.copyBy(spuDO, skuDOMap.get(spuDO.getSpuId()));
            dataList.add(data);
        }
        return ConfirmOrderResponse.copyBy(spuDOS, skuDOMap, countMap);
    }
}
