package com.youngtao.uac.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
public class AddShippingAddressReq {
    private String shippingAddrId;

    private String address;

    private String detail;

    /**
     * 邮政编码
     */
    private String postcode;

    /**
     * 收件人
     */
    private String consignee;

    private String telephone;

    private Boolean isDefault = false;
}
