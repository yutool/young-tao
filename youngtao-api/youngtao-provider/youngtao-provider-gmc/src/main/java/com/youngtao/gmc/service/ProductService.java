package com.youngtao.gmc.service;

import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.youngtao.web.model.PageArg;

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
     * @return data
     */
    ProductData getBySpuId(String id);

    /**
     * 根据skuId查询信息
     * @param request skuIds
     * @return list
     */
    List<ConfirmOrderResponse> confirmOrder(ConfirmOrderRequest request);

    List<SpuSkuData>  listRecommendProduct(PageArg arg);
}
