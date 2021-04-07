package com.youngtao.gsc.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Data
public class CreateOrderRequest {
    private String skuId;

    private String shippingAddressId;

    private String remark;

    private Boolean isCart;
}
