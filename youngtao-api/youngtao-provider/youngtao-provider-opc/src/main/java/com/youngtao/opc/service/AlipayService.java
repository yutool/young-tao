package com.youngtao.opc.service;

import com.youngtao.opc.model.request.AlipayAppCheckRequest;
import com.youngtao.opc.model.request.AlipayRequest;
import com.youngtao.opc.model.request.TradeRefundRequest;
import com.youngtao.opc.model.response.TradeRefundResponse;

import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface AlipayService {
    String appPay(AlipayRequest request);

    String payNotify(Map<String, String> resultMap);

    boolean check(AlipayAppCheckRequest request);

    /**
     * 网页支付
     * @param request
     * @return
     */
    String webPay(AlipayRequest request);

    /**
     * 退款
     * @param request
     * @return
     */
    TradeRefundResponse tradeRefund(TradeRefundRequest request);
}
