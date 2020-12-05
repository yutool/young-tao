package com.youngtao.gmc.model.convert;

import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.request.AddProductRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/05
 */
@Mapper(componentModel = "spring")
public interface SkuConvert {

    @IterableMapping(qualifiedByName = "addProductRequestToSku")
    List<SkuDO> toSku(List<AddProductRequest.Sku> skuList);

    @Named("addProductRequestToSku")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skuId", ignore = true)
    @Mapping(target = "spuId", ignore = true)
    @Mapping(target = "saleNum", ignore = true)
    @Mapping(target = "freezeNum", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    SkuDO toSku(AddProductRequest.Sku sku);

}
