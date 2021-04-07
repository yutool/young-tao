package com.youngtao.opc.api.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class AlipayTradeRefundDTO {
    private String outTradeNo;

    private String tradeNo;

    private BigDecimal refundFee;
}
