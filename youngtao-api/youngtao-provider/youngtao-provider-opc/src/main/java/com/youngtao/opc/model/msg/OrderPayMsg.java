package com.youngtao.opc.model.msg;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/01/31
 */
@Data
public class OrderPayMsg {
    private String paymentId;
    private Integer payType;
}
