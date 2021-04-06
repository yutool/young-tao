package com.youngtao.opc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.model.dto.OrderPayRecordDTO;
import com.youngtao.opc.api.service.fallback.OrderPayRecordFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@FeignClient(value = "youngtao-opc-serve", contextId = "orderPay", fallback = OrderPayRecordFeignFallback.class)
public interface OrderPayRecordFeign {

    /**
     * 返回
     * @return paymentId
     */
    @PostMapping("/api/opc/orderPayRecord/add")
    RpcResult<String> addRecord(@RequestBody AddPayRecordArg arg);

    /**
     * 获取交易记录
     * @param paymentId
     */
    @GetMapping("/api/opc/orderPayRecord/getByPaymentId")
    RpcResult<OrderPayRecordDTO> getByPaymentId(@RequestParam("paymentId") String paymentId);
}
