package com.youngtao.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户收货地址(UacShippingAddress)实体类
 *
 * @author makejava
 * @since 2021-03-30 15:12:17
 */
@Data
@TableName("uac_shipping_address")
public class ShippingAddress implements Serializable {
    private static final long serialVersionUID = 424006524426139180L;

    private Long id;

    private String shippingAddrId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货人姓名
     */
    private String consignee;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 详细地址信息
     */
    private String detail;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 是否默认
     */
    private Integer isDefault;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}