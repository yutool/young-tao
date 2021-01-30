package com.youngtao.opc.model.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Data
public class AlipayAppRequest {
    private String paymentId;
    private String subject;
    private String body;
    private BigDecimal money;
}
