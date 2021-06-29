package com.youngtao.omc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息实体类
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Data
@TableName("omc_order")
public class OrderDO implements Serializable {
    private static final long serialVersionUID = -93019552150606160L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商家id
     */
    private String merchantId;

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
     * 支付方式
     */
    private Integer payType;

    /**
     * 订单支付号，给支付平台的订单Id
     */
    private String paymentId;

    @TableField("is_deleted")
    private Boolean deleted;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}