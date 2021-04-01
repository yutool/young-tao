package com.youngtao.gmc.model.convert;

import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.model.domain.CategoryDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/03
 */
@Mapper(componentModel = "spring")
public interface CategoryConvert {

    @Named("toCategoryData")
    @Mapping(target = "children", ignore = true)
    CategoryData toCategoryData(CategoryDO data);

    @IterableMapping(qualifiedByName = "toCategoryData")
    List<CategoryData> toCategoryData(List<CategoryDO> data);
}
