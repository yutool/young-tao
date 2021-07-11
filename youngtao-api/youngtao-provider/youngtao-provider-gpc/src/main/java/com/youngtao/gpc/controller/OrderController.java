package com.youngtao.gpc.controller;

import com.youngtao.gpc.model.req.CreateOrderReq;
import com.youngtao.gpc.service.OrderService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@ResponseWrapper
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public String createOrder(@RequestBody CreateOrderReq request) {
        return orderService.createOrder(request);
    }
}
