package com.youngtao.omc.model.domain;

import com.youngtao.web.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息实体类
 *
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -93019552150606160L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 客户id
     */
    private String userId;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 实付金额
     */
    private BigDecimal payMoney;

    /**
     * 收货地址
     */
    private String shippingAddressId;

    /**
     * 运费
     */
    private BigDecimal postage;

    /**
     * 总量 g
     */
    private Integer weight;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0-普通订单 1-秒杀订单
     */
    private Integer type;

    /**
     * 0-已完成 1-待付款 2-待发货 3-待评价 4-退款退货
     */
    private Integer status;

    /**
     * 订单支付号，给支付平台的订单Id
     */
    private Long paymentId;

    /**
     * 支付流水号
     */
    private String transactionId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付方式
     */
    private Integer payType;

}