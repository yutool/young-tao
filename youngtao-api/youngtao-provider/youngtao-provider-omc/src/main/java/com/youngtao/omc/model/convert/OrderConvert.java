package com.youngtao.omc.model.convert;

import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.domain.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@Mapper(componentModel = "spring")
public interface OrderConvert {

    @Mapping(target = "shopName", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    OrderData toOrderData(OrderDO orderDO);

    List<OrderData> toOrderData(List<OrderDO> orderDO);
}
