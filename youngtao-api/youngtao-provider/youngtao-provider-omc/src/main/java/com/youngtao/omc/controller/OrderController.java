package com.youngtao.omc.controller;

import com.youngtao.core.result.ResponseResult;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseResult<Long> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Long response = orderService.createOrder(request);
        return ResponseResult.success(response);
    }
}
