package com.youngtao.opc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.opc.service.OrderPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@RestController
public class OrderPayRecordFeignClient implements OrderPayRecordFeign {
    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Override
    public RpcResult<String> addRecord(@RequestBody AddPayRecordArg arg) {
        String paymentId = orderPayRecordService.addRecord(arg.getPaymentId(), arg.getMoney());
        return RpcResult.success(paymentId);
    }
}
