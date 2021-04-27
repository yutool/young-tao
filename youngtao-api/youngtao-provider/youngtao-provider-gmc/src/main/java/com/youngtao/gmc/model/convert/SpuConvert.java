package com.youngtao.gmc.model.convert;

import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.request.AddProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author ankoye@qq.com
 * @date 2020/12/05
 */
@Mapper(componentModel = "spring")
public interface SpuConvert {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "spuId", ignore = true)
    @Mapping(target = "merchantId", ignore = true)
    @Mapping(target = "shopName", ignore = true)
    @Mapping(target = "saleNum", ignore = true)
    @Mapping(target = "commentNum", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    SpuDO toSpu(AddProductRequest request);
}
