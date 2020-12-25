package com.youngtao.gsc.model.convert;

import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.domain.SkuDO;
import org.mapstruct.Mapper;

/**
 * @author ankoye@qq.com
 * @date 2020/12/25
 */
@Mapper(componentModel = "spring")
public interface SkuConvert {

    SkuData toSkuData(SkuDO skuDO);
}
