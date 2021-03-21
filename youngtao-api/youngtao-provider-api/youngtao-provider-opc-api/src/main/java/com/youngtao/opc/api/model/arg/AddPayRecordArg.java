package com.youngtao.opc.api.model.arg;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@Data
public class AddPayRecordArg {
    private String paymentId;

    private BigDecimal money;
}
