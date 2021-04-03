package com.youngtao.uac.model.data;

import lombok.Data;

import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@Data
public class ShippingAddressData {

    private Long id;

    private String shippingAddrId;

    private String userId;

    private String consignee;

    private String telephone;

    private String address;

    private String detail;

    private String postcode;

    private Boolean isDefault;

    private Date updateTime;

    private Date createTime;
}
