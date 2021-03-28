package com.youngtao.omc.model.convert;

import com.youngtao.omc.model.data.OrderItemData;
import com.youngtao.omc.model.domain.OrderItemDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@Mapper(componentModel = "spring")
public interface OrderItemConvert {

    OrderItemData toOrderItemData(OrderItemDO orderItemDO);

    List<OrderItemData> toOrderItemData(List<OrderItemDO> orderItemDO);

}
