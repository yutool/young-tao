package com.youngtao.gmc.model.convert;

import com.youngtao.core.util.ProductUtils;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/04
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    @Named("skuDOToSku")
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "type", ignore = true)
    ProductData.Sku skuDOToSku(SkuDO skuDO);

    @Named("skuDOListToSkuList")
    @IterableMapping(qualifiedByName = "skuDOToSku")
    List<ProductData.Sku> skuDOListToSkuList(List<SkuDO> list);

    @Mapping(target = "skuList", source = "skuDOList", qualifiedByName = "skuDOListToSkuList")
    ProductData toProductData(SpuDO spuDO, List<SkuDO> skuDOList);

    default ProductData toProductDataWithTitle(SpuDO spuDO, List<SkuDO> skuDOList) {
        ProductData productData = this.toProductData(spuDO, skuDOList);
        for (ProductData.Sku sku : productData.getSkuList()) {
            sku.setTitle(ProductUtils.generateTitle(spuDO.getSpu(), sku.getSku()));
        }
        return productData;
    }

    @Mapping(target = "spuId", source = "spuDO.spuId")
    @Mapping(target = "saleNum", source = "spuDO.saleNum")
    @Mapping(target = "images", source = "skuDO.images")
    @Mapping(target = "title", expression = "java(com.youngtao.core.util.ProductUtils.generateTitle(spuDO.getSpu(), skuDO.getSku()))")
    SpuSkuData toSpuSkuData(SpuDO spuDO, SkuDO skuDO);
}
