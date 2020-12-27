package com.youngtao.gsc.service;

import com.youngtao.gsc.model.data.OrderQueue;
import com.youngtao.gsc.model.request.CreateOrderRequest;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
public interface OrderService {
    /**
     * 排队
     * @param request request
     * @param userId userId
     * @return queue
     */
    OrderQueue createOrder(CreateOrderRequest request, String userId);
}
