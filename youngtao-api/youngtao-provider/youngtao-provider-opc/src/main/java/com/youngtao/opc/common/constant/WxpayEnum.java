package com.youngtao.opc.common.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public enum WxpayEnum {
    /** 支付成功 */
    SUCCESS("SUCCESS");

    @Getter
    private final String value;

    WxpayEnum(String value) {
        this.value = value;
    }
}
