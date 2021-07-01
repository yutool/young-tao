package com.youngtao.gpc.model.convert;

import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.model.data.ProductData;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.gpc.model.domain.SkuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/25
 */
@Mapper(componentModel = "spring")
public interface SkuConvert {

    List<GpcSkuDTO> toSkuDTO(List<SkuData> data);

    GpcSkuDTO toSkuDTO(SkuData data);

    @Mapping(target = "title", ignore = true)
    SkuData toSkuData(SkuDO skuDO);

    ProductData.Sku toProductSku(SkuDO skuDO);

}
