package com.youngtao.omc.service;

import com.github.pagehelper.PageInfo;
import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.req.CreateOrderReq;
import com.youngtao.omc.model.req.GetMerchantOrderReq;
import com.youngtao.omc.model.req.GetUserOrderReq;
import com.youngtao.omc.model.req.OrderRefundReq;
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
    String createOrder(CreateOrderReq request);

    Integer queryStatus(String paymentId);

    /**
     * 获取用户订单
     */
    PageInfo<OrderData> getUserOrder(GetUserOrderReq request);

    /**
     * 获取商家订单
     */
    PageInfo<OrderData> getMerchantOrder(GetMerchantOrderReq request);

    /**
     * 订单退款
     * @param request
     */
    void orderRefund(OrderRefundReq request);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrder(String orderId);

    /**
     * 恢复订单
     * @param orderId
     */
    void recoverOrder(String orderId);

    /**
     * 修改订单信息
     */
    void updateStatusByMerchant(String orderId, Integer status);

    /**
     * 修改订单信息
     */
    void updateStatusByUser(String orderId, int status);
}
