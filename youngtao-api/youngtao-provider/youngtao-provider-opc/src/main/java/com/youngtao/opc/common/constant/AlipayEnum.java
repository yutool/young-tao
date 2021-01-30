package com.youngtao.opc.common.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public enum AlipayEnum {

    /** 交易成功 */
    FINISHED("TRADE_FINISHED"),

    /** 支付成功 */
    SUCCESS("TRADE_SUCCESS"),

    /** 交易创建 */
    BUYER_PAY("WAIT_BUYER_PAY"),

    /** 交易关闭 */
    CLOSED("TRADE_CLOSED");

    @Getter
    private final String value;

    AlipayEnum(String value) {
        this.value = value;
    }

}
