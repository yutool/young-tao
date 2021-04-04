package com.youngtao.omc.service;

import com.github.pagehelper.PageInfo;
import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.model.request.GetUserOrderRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
public interface OrderService extends IService<OrderDO> {

    /**
     * 创建订单
     * @param request request
     * @return boolean
     */
    String createOrder(CreateOrderRequest request, String userId);

    Integer queryStatus(String paymentId);

    /**
     * 获取用户订单
     */
    PageInfo<OrderData> getUserOrder(GetUserOrderRequest request, String userId);
}
