package com.youngtao.gmc.service;

import com.github.pagehelper.PageInfo;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.model.req.AddProductReq;
import com.youngtao.gmc.model.req.ConfirmOrderReq;
import com.youngtao.gmc.model.req.GetMerchantProductReq;
import com.youngtao.gmc.model.req.SearchProductReq;
import com.youngtao.gmc.model.res.ConfirmOrderRes;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public interface ProductService {

    /**
     * 搜索商品，暂时这样吧
     */
    PageInfo<SpuSkuData> searchProduct(SearchProductReq request);

    /**
     * 添加商品
     * @param request product
     * @return bool
     */
    void addProduct(AddProductReq request);

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
    List<ConfirmOrderRes> confirmOrder(ConfirmOrderReq request);

    /**
     * 支付成功
     * @param query
     */
    void paySuccess(List<UpdateStockQuery> query);

    /**
     * 获取商家订单
     */
    PageInfo<ProductData> getMerchantProduct(GetMerchantProductReq request);

    /**
     * 获取SkuSpu
     */
    SpuSkuData getSpuSku(String skuId);

}
