package com.youngtao.omc.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class OrderRefundRequest {
    private String orderId;
    private String refundReason;
}
