package com.youngtao.opc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.model.arg.TradeRefundArg;
import com.youngtao.opc.api.model.dto.AlipayTradeRefundDTO;
import com.youngtao.opc.api.service.fallback.AlipayFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@FeignClient(value = "youngtao-opc-serve", contextId = "alipay", fallback = AlipayFeignFallback.class)
public interface AlipayFeign {

    @PostMapping("/api/opc/alipay/tradeRefund")
    RpcResult<AlipayTradeRefundDTO> tradeRefund(@RequestBody TradeRefundArg arg);
}
