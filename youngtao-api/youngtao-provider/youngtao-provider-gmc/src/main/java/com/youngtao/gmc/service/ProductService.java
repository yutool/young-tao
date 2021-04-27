package com.youngtao.gmc.service;

import com.github.pagehelper.PageInfo;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.request.GetMerchantProductRequest;
import com.youngtao.gmc.model.request.SearchProductRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public interface ProductService {

    /**
     * 搜索商品，暂时这样吧
     */
    PageInfo<SpuSkuData> searchProduct(SearchProductRequest request);

    /**
     * 添加商品
     * @param request product
     * @return bool
     */
    void addProduct(AddProductRequest request);

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

    /**
     * 支付成功
     * @param query
     */
    void paySuccess(List<UpdateStockQuery> query);

    /**
     * 获取商家订单
     */
    PageInfo<ProductData> getMerchantProduct(GetMerchantProductRequest request);

    /**
     * 获取SkuSpu
     */
    SpuSkuData getSpuSku(String skuId);

}
