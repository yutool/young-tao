package com.youngtao.gpc.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Data
public class CreateOrderReq {
    private String skuId;

    private String shippingAddressId;

    private String remark;

    private Boolean isCart;
}
