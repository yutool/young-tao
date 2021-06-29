package com.youngtao.gmc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */
@Data
@TableName("gmc_coupon")
public class CouponDO implements Serializable {
    private static final long serialVersionUID = -74564248361768128L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String couponId;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 店名
     */
    private String shopName;

    /**
     * 满多少
     */
    private BigDecimal reach;

    /**
     * 减多少
     */
    private BigDecimal reduction;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 优惠券类型 1-店铺 2-商品
     */
    private Integer type;

    /**
     * 对应type的id
     */
    private String targetId;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}