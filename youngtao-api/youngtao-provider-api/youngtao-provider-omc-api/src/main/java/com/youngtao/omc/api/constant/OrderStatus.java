package com.youngtao.omc.api.constant;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
public class OrderStatus {

    /**
     * 已完成
     */
    public static final int COMPLETED = 0;

    /**
     * 创建中
     */
    public static final int CREATING = 1;

    /**
     * 待付款
     */
    public static final int PAYMENT = 2;

    /**
     * 待发货
     */
    public static final int DELIVERY = 3;

    /**
     * 待收货
     */
    public static final int RECEIVING = 4;

    /**
     * 待评价
     */
    public static final int COMMENT = 5;

    /**
     * 创建失败
     */
    public static final int FAILED = 9;

    /**
     * 退货中
     */
    public static final int RETURN = 11;

    /**
     * 已退款
     */
    public static final int REFUND = 12;

    /**
     * 关闭
     */
    public static final int CLOSE = 100;

}
