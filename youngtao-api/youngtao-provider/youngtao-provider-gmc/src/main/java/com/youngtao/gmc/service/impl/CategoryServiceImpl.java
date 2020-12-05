package com.youngtao.gmc.service.impl;

import com.youngtao.gmc.mapper.CategoryMapper;
import com.youngtao.gmc.model.convert.CategoryConvert;
import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.model.domain.CategoryDO;
import com.youngtao.gmc.service.CategoryService;
import com.youngtao.web.support.BaseService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Slf4j
@Service
public class CategoryServiceImpl extends BaseService<CategoryDO> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryConvert categoryConvert;

    @Override
    public List<CategoryData> getCategory() {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);
        Map<String, CategoryData> map = Maps.newLinkedHashMap();
        for (CategoryDO category : categoryDOS) {
            map.put(category.getCategoryId(), categoryConvert.toCategoryData(category));
        }
        // Convert to tree structure
        for (CategoryData data : map.values()) {
            String parentId = data.getParentId();
            if ("0".equals(parentId)) {
                continue;
            }
            CategoryData categoryData = map.get(parentId);
            if (categoryData == null) {
                log.warn("Invalid directory categoryId = {}", data.getCategoryId());
                continue;
            }
            if (categoryData.getChildren() == null) {
                categoryData.setChildren(Lists.newArrayList());
            }
            categoryData.getChildren().add(data);
        }
        return map.values().stream().filter(item -> "0".equals(item.getParentId())).collect(Collectors.toList());
    }
}
