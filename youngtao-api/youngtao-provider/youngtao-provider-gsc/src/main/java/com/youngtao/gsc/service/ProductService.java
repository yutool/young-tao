package com.youngtao.gsc.service;

import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.request.ConfirmOrderRequest;
import com.youngtao.gsc.model.response.ConfirmOrderResponse;
import com.youngtao.gsc.model.response.GetSeckillPageResponse;

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

    GetSeckillPageResponse getSeckillPage();

    /**
     * 确认订单
     * @param request
     */
    ConfirmOrderResponse confirmOrder(ConfirmOrderRequest request);
}
