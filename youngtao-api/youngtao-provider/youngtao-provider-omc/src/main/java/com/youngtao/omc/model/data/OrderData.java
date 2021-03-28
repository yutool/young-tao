package com.youngtao.omc.model.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class OrderData {
    private String orderId;

    private String merchantId;

    private String shopName;

    private String userId;

    private BigDecimal totalPrice;

    private BigDecimal actualPrice;

    private BigDecimal payMoney;

    private String shippingAddressId;

    private BigDecimal postage;

    private Integer weight;

    private String remark;

    private Integer type;

    private Integer status;

    private String paymentId;

    private Integer payType;

    List<OrderItemData> orderItemList = Lists.newArrayList();
}
