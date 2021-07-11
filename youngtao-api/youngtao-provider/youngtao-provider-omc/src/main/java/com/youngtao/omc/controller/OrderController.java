package com.youngtao.omc.controller;

import com.github.pagehelper.PageInfo;
import com.youngtao.core.data.IdArg;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.req.CreateOrderReq;
import com.youngtao.omc.model.req.GetMerchantOrderReq;
import com.youngtao.omc.model.req.GetUserOrderReq;
import com.youngtao.omc.model.req.OrderRefundReq;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@RestController
@ResponseWrapper
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/getUserOrder")
    public PageInfo<OrderData> getUserOrder(@RequestBody GetUserOrderReq request) {
        return orderService.getUserOrder(request);
    }

    @PostMapping("/getMerchantOrder")
    public PageInfo<OrderData> getMerchantOrder(@RequestBody GetMerchantOrderReq request) {
        return orderService.getMerchantOrder(request);
    }

    @PostMapping("/create")
    public String createOrder(@Valid @RequestBody CreateOrderReq request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/orderRefund")
    public void orderRefund(@RequestBody OrderRefundReq request) {
        orderService.orderRefund(request);
    }

    @PostMapping("/queryStatus")
    public Integer queryStatus(@RequestBody IdArg idArg) {
        return orderService.queryStatus(idArg.getId());
    }

    @PostMapping("/delete")
    public void deleteOrder(@RequestBody IdArg idArg) {
        orderService.deleteOrder(idArg.getId());
    }

    @PostMapping("/recover")
    public void recoverOrder(@RequestBody IdArg idArg) {
        orderService.recoverOrder(idArg.getId());
    }

    @PostMapping("/delivery")
    public void delivery(@RequestBody IdArg arg) {
        orderService.updateStatusByMerchant(arg.getId(), OrderStatus.RECEIVING);
    }

    @PostMapping("/accept")
    public void accept(@RequestBody IdArg arg) {
        orderService.updateStatusByUser(arg.getId(), OrderStatus.COMPLETED);
    }
}