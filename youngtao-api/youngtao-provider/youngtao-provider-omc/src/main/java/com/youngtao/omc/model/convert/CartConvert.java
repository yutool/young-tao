package com.youngtao.omc.model.convert;

import com.youngtao.omc.model.data.CartData;
import com.youngtao.omc.model.domain.CartDO;
import com.youngtao.omc.model.request.AddCartRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Mapper(componentModel = "spring")
public interface CartConvert {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "oldPrice", ignore = true)
    @Mapping(target = "spu", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    CartDO toCart(AddCartRequest request);

    @Named("toCartData")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "spuId", ignore = true)
    @Mapping(target = "sku", ignore = true)
    CartData toCartData(CartDO cartDO);

    @IterableMapping(qualifiedByName = "toCartData")
    List<CartData> toCartData(List<CartDO> cartDOList);
}
