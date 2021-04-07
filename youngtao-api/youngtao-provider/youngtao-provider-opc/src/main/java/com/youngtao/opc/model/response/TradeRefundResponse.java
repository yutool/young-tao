package com.youngtao.opc.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class TradeRefundResponse {
    private String outTradeNo;

    private String tradeNo;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;
}
