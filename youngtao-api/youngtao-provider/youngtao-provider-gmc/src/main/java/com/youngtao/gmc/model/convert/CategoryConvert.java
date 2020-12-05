package com.youngtao.gmc.model.convert;

import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.model.domain.CategoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author ankoye@qq.com
 * @date 2020/12/03
 */
@Mapper(componentModel = "spring")
public interface CategoryConvert {

    @Mappings({
            @Mapping(target = "children", ignore = true)
    })
    CategoryData toCategoryData(CategoryDO data);
}
