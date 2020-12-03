package com.youngtao.omc.model.request;

import com.google.common.collect.Lists;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
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
public class CreateOrderRequest {

    @Valid
    @NotEmpty
    private List<Order> data;

    @NotBlank
    private String shippingAddressId;

    @Data
    public static class Order {

        private String remark;

        @Valid
        @NotEmpty
        private List<Item> orderItem;
    }

    @Data
    public static class Item {

        @NotBlank
        private String skuId;

        @Min(value = 1)
        private Integer num;
    }

    public OrderDO convertToOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setShippingAddressId(shippingAddressId);
        orderDO.setRemark(order.getRemark());
        return orderDO;
    }

    public List<OrderItemDO> convertToOrderItem(Order order) {
        List<OrderItemDO> result = Lists.newArrayList();
        for (Item item : order.getOrderItem()) {
            OrderItemDO orderItemDO = new OrderItemDO();
            orderItemDO.setSkuId(item.getSkuId());
            orderItemDO.setNum(item.getNum());
            result.add(orderItemDO);
        }
        return result;
    }

}
