package com.youngtao.omc.controller;

import com.youngtao.core.data.IdArg;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public String createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userId = "0";
        return orderService.createOrder(request, userId);
    }

    @PostMapping("/queryStatus")
    public Integer queryStatus(@RequestBody IdArg idArg) {
        return orderService.queryStatus(idArg.getId());
    }
}
