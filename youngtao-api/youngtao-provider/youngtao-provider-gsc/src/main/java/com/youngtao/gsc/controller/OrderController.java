package com.youngtao.gsc.controller;

import com.youngtao.core.result.ResponseResult;
import com.youngtao.gsc.model.data.OrderQueue;
import com.youngtao.gsc.model.request.CreateOrderRequest;
import com.youngtao.gsc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public ResponseResult<OrderQueue> createOrder(@RequestBody CreateOrderRequest request) {
        String userId = "0";
        OrderQueue order = orderService.createOrder(request, userId);
        return ResponseResult.success(order);
    }

}
