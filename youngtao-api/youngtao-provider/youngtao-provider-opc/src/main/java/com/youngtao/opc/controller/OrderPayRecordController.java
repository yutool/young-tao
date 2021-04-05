package com.youngtao.opc.controller;

import com.youngtao.opc.model.data.OrderPayRecordData;
import com.youngtao.opc.service.OrderPayRecordService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@ResponseWrapper
@RestController
@RequestMapping("/orderPayRecord")
public class OrderPayRecordController {

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @GetMapping("/{id}")
    public OrderPayRecordData getByPaymentId(@PathVariable String id) {
        return orderPayRecordService.getByPaymentId(id);
    }
}
