package com.youngtao.opc.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Data
public class WxpayAppReq {
    private String paymentId;
    private String body;
}
