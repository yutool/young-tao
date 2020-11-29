package com.youngtao.omc.model.request;

import com.google.common.collect.Lists;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import lombok.Data;

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

    private List<Order> data;

    @Data
    public static class Order {
        @NotBlank
        private String shippingAddressId;

        private String remark;

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
        orderDO.setShippingAddressId(order.getShippingAddressId());
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
