package com.youngtao.gmc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.lang.JsonList;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.constant.ProductType;
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
import com.youngtao.gmc.model.query.UpdateSaleQuery;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.youngtao.gmc.service.ProductService;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.GscProductFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Slf4j
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
    @Autowired
    private GscProductFeign gscProductFeign;

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
        ProductData productData = productConvert.toProductDataWithTitle(spuDO, skuDOList);
        // 替换活动商品的价格
        List<String> skuIds = skuDOList.stream().map(SkuDO::getSkuId).collect(Collectors.toList());
        RpcResult<List<GscSkuDTO>> skuResult = gscProductFeign.listByIds(skuIds);
        RpcResultUtils.checkNotNull(skuResult);
        for (ProductData.Sku sku : productData.getSkuList()) {
            for (GscSkuDTO dto : skuResult.getData()) {
                if (Objects.equals(sku.getSkuId(), dto.getSkuId())) {
                    sku.setPrice(dto.getPrice());
                    sku.setOldPrice(dto.getOldPrice());
                    sku.setType(ProductType.SECKILL);
                }
            }
        }
        return productData;
    }

    @Override
    public List<ConfirmOrderResponse> confirmOrder(ConfirmOrderRequest request) {
        Map<String, Integer> countMap = request.getSkuList().stream().collect(Collectors.toMap(ConfirmOrderRequest.Data::getSkuId, ConfirmOrderRequest.Data::getCount));
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(countMap.keySet());

        Map<String, List<SkuDO>> skuDOMap = Maps.newHashMap();
        for (SkuDO skuDO : skuDOList) {
            List<SkuDO> list = skuDOMap.getOrDefault(skuDO.getSpuId(), Lists.newArrayList());
            list.add(skuDO);
            skuDOMap.put(skuDO.getSpuId(), list);
        }
        List<SpuDO> spuDOList = spuMapper.listBySpuIds(skuDOMap.keySet());

        // Processing return value
        Map<String, ConfirmOrderResponse> responseMap = Maps.newHashMap();
        for (SpuDO spuDO : spuDOList) {
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
                sku.setSpu(spuDO.getSpu());
                sku.setSku(skuDO.getSku());
                sku.setPrice(skuDO.getPrice());
                sku.setImage(skuDO.getImages().get(0));
                sku.setCount(countMap.get(skuDO.getSkuId()));
                sku.setServe(spuDO.getServe());
                response.getSkuList().add(sku);
            }
            responseMap.put(spuDO.getMerchantId(), response);
        }
        return Lists.newArrayList(responseMap.values());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(List<UpdateStockQuery> query) {
        skuMapper.paySuccess(query);
        // 计算spu对应的个数
        Set<String> skuIds = query.stream().map(UpdateStockQuery::getSkuId).collect(Collectors.toSet());
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(skuIds);
        Map<String, String> map = skuDOList.stream().collect(Collectors.toMap(SkuDO::getSkuId, SkuDO::getSpuId));

        Map<String, UpdateSaleQuery> spuMap = Maps.newHashMap();
        for (UpdateStockQuery item : query) {
            String spuId = map.get(item.getSkuId());
            UpdateSaleQuery saleQuery = spuMap.get(spuId);
            if (saleQuery == null) {
                saleQuery = new UpdateSaleQuery();
                saleQuery.setNum(item.getNum());
                saleQuery.setSpuId(spuId);
                spuMap.put(spuId, saleQuery);
            } else {
                saleQuery.setNum(saleQuery.getNum() + item.getNum());
            }
        }
        spuMapper.paySuccess(spuMap.values());
    }
}
