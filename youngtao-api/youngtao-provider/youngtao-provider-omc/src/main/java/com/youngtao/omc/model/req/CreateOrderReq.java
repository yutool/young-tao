package com.youngtao.omc.model.req;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Data
public class CreateOrderReq {
    @Valid
    @NotEmpty
    private List<Order> orderList;

    @NotBlank
    private String shippingAddressId;

    private Boolean isCart;

    @Data
    public static class Order {
        @Valid
        @NotEmpty
        private List<OrderItem> orderItem;

        private String remark;
    }

    @Data
    public static class OrderItem {

        @NotBlank
        private String skuId;

        @Min(value = 1)
        private Integer count;
    }
}
