package com.youngtao.omc.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class OrderRefundReq {
    private String orderId;
    private String refundReason;
}
