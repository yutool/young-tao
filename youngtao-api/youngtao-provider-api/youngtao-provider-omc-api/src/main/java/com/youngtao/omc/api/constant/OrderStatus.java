package com.youngtao.omc.api.constant;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
public class OrderStatus {

    /**
     * 已完成
     */
    public static final Integer COMPLETED = 0;

    /**
     * 待付款
     */
    public static final Integer CREATING = 1;

    /**
     * 待付款
     */
    public static final Integer PAYMENT = 2;

    /**
     * 待发货
     */
    public static final Integer DELIVERY = 3;

    /**
     * 待评价
     */
    public static final Integer COMMENT = 4;

    /**
     * 退货中
     */
    public static final Integer RETURN = 5;

    /**
     * 关闭
     */
    public static final Integer CLOSE = 9;

}
