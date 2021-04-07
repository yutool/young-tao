package com.youngtao.opc.model.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class TradeRefundRequest {
    private String outTradeNo;
    private String tradeNo;
    private BigDecimal refundAmount;
    private String outRequestNo;
    private String refundReason;
}
