package com.ankoye.youngtao.gmc.service;

import com.ankoye.youngtao.gmc.model.data.ProductData;
import com.ankoye.youngtao.gmc.model.request.AddProductRequest;
import com.ankoye.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.ankoye.youngtao.gmc.model.response.ConfirmOrderResponse;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public interface ProductService {

    /**
     * 添加商品
     * @param request product
     * @return bool
     */
    Boolean addProduct(AddProductRequest request);

    /**
     * 根据SpuId获取商品
     * @param id spuId
     */
    ProductData getBySpuId(String id);

    /**
     * 根据skuId查询信息
     * @param request skuIds
     */
    List<ConfirmOrderResponse> confirmOrder(ConfirmOrderRequest request);
}
