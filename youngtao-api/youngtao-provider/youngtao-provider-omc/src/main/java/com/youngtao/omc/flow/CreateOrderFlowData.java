package com.youngtao.omc.flow;

import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Data
public class CreateOrderFlowData {

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
        // type == 2
        private String menu;

        private String skuId;

        private Integer count;
    }
}
