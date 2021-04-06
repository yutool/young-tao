package com.youngtao.gsc.controller;

import com.youngtao.gsc.model.request.CreateOrderRequest;
import com.youngtao.gsc.service.OrderService;
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
    public String createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
