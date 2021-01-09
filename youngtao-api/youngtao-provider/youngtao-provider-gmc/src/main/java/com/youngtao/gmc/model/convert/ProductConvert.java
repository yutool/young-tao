package com.youngtao.gmc.model.convert;

import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/04
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    @Mapping(target = "skuList", source = "skuDOList")
    ProductData toProductData(SpuDO spuDO, List<SkuDO> skuDOList);

    @Mapping(target = "spuId", source = "spuDO.spuId")
    @Mapping(target = "saleNum", source = "spuDO.saleNum")
    @Mapping(target = "images", source = "skuDO.images")
    SpuSkuData toSpuSkuData(SpuDO spuDO, SkuDO skuDO);
}
