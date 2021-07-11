package com.youngtao.gpc.service;

import com.github.pagehelper.PageInfo;
import com.youngtao.gpc.model.data.ProductData;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.gpc.model.req.ConfirmOrderReq;
import com.youngtao.gpc.model.req.GetMerchantProductReq;
import com.youngtao.gpc.model.res.ConfirmOrderRes;
import com.youngtao.gpc.model.res.GetSeckillPageRes;

import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public interface ProductService {

    /**
     * 获取当前秒杀时间段的商品
     * @param time time
     * @param page page
     * @param size size
     * @return list
     */
    Set<SkuData> listByTime(String time, Integer page, Integer size);

    /**
     * 获取秒杀商品详情
     *
     * @param time time
     * @param skuId id
     * @return record
     */
    ProductData detail(String time, String skuId);

    GetSeckillPageRes getSeckillPage();

    /**
     * 确认订单
     * @param request
     */
    ConfirmOrderRes confirmOrder(ConfirmOrderReq request);

    /**
     * 获取商家活动的商品
     */
    PageInfo<ProductData> getMerchantProduct(GetMerchantProductReq request);
}
