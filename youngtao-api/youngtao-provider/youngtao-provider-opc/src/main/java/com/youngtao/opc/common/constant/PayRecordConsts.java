package com.youngtao.opc.common.constant;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
public interface PayRecordConsts {
    /** 未支付 */
    int UNPAID = 0;
    /** 支付了 */
    int PAID = 1;
    /** 支付失败 */
    int PAYMENT_FAILED = 2;

    /** 支付宝 */
    int ALIPAY_TYPE = 1;
    /** 微信 */
    int WXPAY_TYPE = 2;
    /** 其他 */
    int OTHER_TYPE = 3;
}
