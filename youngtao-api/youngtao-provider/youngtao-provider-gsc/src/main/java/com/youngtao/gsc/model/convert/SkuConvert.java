package com.youngtao.gsc.model.convert;

import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.domain.SkuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/25
 */
@Mapper(componentModel = "spring")
public interface SkuConvert {

    List<GscSkuDTO> toSkuDTO(List<SkuData> data);

    GscSkuDTO toSkuDTO(SkuData data);

    @Mapping(target = "title", ignore = true)
    SkuData toSkuData(SkuDO skuDO);

    ProductData.Sku toProductSku(SkuDO skuDO);

}
