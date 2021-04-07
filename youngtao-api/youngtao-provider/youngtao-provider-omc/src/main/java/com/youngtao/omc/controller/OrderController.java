package com.youngtao.omc.controller;

import com.github.pagehelper.PageInfo;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.data.IdArg;
import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.model.request.GetUserOrderRequest;
import com.youngtao.omc.model.request.OrderRefundRequest;
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
    public PageInfo<OrderData> getUserOrder(@RequestBody GetUserOrderRequest request) {
        AuthInfo authInfo = AuthContext.get();
        return orderService.getUserOrder(request, authInfo.getUserId());
    }

    @PostMapping("/create")
    public String createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/orderRefund")
    public void orderRefund(@RequestBody OrderRefundRequest request) {
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
}
