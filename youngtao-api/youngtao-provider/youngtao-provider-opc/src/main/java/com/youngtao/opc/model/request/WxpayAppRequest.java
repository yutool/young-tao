package com.youngtao.opc.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Data
public class WxpayAppRequest {
    private String paymentId;
    private String body;
    private String money;
}
