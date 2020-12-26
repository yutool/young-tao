package com.youngtao.gsc.model.convert;

import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gsc.model.data.ProductData;
import org.mapstruct.Mapper;

/**
 * @author ankoye@qq.com
 * @date 2020/12/26
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductData toProductData(ProductDTO productDTO);
}
