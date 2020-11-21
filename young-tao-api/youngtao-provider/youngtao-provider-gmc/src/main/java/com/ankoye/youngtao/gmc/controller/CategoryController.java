package com.ankoye.youngtao.gmc.controller;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.gmc.model.data.CategoryData;
import com.ankoye.youngtao.gmc.model.entity.CategoryDO;
import com.ankoye.youngtao.gmc.model.response.GetCategoryResponse;
import com.ankoye.youngtao.gmc.service.CategoryService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/test")
    public List<CategoryDO> test() {
        return categoryService.selectAll();
    }

    /**
     * 拉取目录，需要做个缓存
     * @return
     */
    @RequestMapping("/getCategory")
    public ResponseResult<GetCategoryResponse> getCategory() {
        List<CategoryDO> categoryDOS = categoryService.selectAll();
        // 一级目录
        List<CategoryData> categoryList = getChildrenCategory(categoryDOS, "0");
        // 二三级目录
        for (CategoryData first : categoryList) {
            first.setChildren(getChildrenCategory(categoryDOS, first.getCategoryId()));
            for (CategoryData second : first.getChildren()) {
                second.setChildren(getChildrenCategory(categoryDOS, second.getCategoryId()));
            }
        }
        GetCategoryResponse response = new GetCategoryResponse();
        response.setCategoryList(categoryList);
        return ResponseResult.success(response);
    }

    private List<CategoryData> getChildrenCategory(List<CategoryDO> categoryList, String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return Lists.newArrayList();
        }
        return categoryList.stream()
                .filter(item -> parentId.equals(item.getParentId()))
                .map(CategoryData::copyBy)
                .collect(Collectors.toList());
    }

}
