package com.youngtao.omc.api.model.msg;

import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
@Data
public class CreateOrderMessage {

    private List<Order> orderList;

    private String shippingAddressId;

    private Boolean isCart;

    private String userId;

    private String paymentId;

    @Data
    public static class Order {
        private List<OrderItem> orderItem;

        private String remark;
    }

    @Data
    public static class OrderItem {
        private String menu;

        private String skuId;

        private Integer count;
    }
}
