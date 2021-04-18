package com.youngtao.gsc.model.convert;

import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.domain.SkuDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/26
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    @Named("skuDOListToSkuList")
    @IterableMapping(qualifiedByName = "skuDOToSku")
    List<ProductData.Sku> skuDOListToSkuList(List<SkuDO> list);

    @Mapping(target = "skuList", source = "skuDOList", qualifiedByName = "skuDOListToSkuList")
    ProductData toProductData(SpuDTO spuDO, List<SkuDO> skuDOList);
}
