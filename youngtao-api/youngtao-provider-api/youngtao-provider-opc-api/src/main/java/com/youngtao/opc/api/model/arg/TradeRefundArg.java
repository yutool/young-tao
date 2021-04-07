package com.youngtao.opc.api.model.arg;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class TradeRefundArg {

    /**
     * paymentId
     */
    private String outTradeNo;

    private String tradeNo;

    private BigDecimal refundAmount;

    /**
     * orderId
     */
    private String outRequestNo;

    private String refundReason;
}
