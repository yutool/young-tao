package com.youngtao.opc.model.req;

import lombok.Data;


/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Data
public class AlipayReq {
    private String paymentId;
    /**
     * 商品名称
     */
    private String subject;
    /**
     * 商品描述
     */
    private String body;
}
