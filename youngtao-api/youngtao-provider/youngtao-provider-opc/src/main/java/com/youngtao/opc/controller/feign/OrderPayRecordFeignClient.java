package com.youngtao.opc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.opc.service.OrderPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@RestController
public class OrderPayRecordFeignClient implements OrderPayRecordFeign {
    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Override
    public RpcResult<String> addRecord(BigDecimal money) {
        String paymentId = orderPayRecordService.addRecord(money);
        return RpcResult.success(paymentId);
    }
}
