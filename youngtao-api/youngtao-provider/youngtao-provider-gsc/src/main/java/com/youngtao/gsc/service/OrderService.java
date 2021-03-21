package com.youngtao.gsc.service;

import com.youngtao.gsc.model.request.CreateOrderRequest;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
public interface OrderService {
    /**
     * 验证信息
     * @param request request
     * @param userId userId
     */
    String createOrder(CreateOrderRequest request, String userId);
}
