package com.youngtao.opc.service;

import com.youngtao.opc.model.req.AlipayAppCheckReq;
import com.youngtao.opc.model.req.AlipayReq;
import com.youngtao.opc.model.req.TradeRefundReq;
import com.youngtao.opc.model.response.TradeRefundRes;

import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface AlipayService {
    String appPay(AlipayReq request);

    String payNotify(Map<String, String> resultMap);

    boolean check(AlipayAppCheckReq request);

    /**
     * 网页支付
     * @param request
     * @return
     */
    String webPay(AlipayReq request);

    /**
     * 退款
     * @param request
     * @return
     */
    TradeRefundRes tradeRefund(TradeRefundReq request);
}
