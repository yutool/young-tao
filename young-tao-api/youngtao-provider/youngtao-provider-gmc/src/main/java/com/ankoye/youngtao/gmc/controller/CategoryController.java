package com.ankoye.youngtao.gmc.controller;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.gmc.model.data.CategoryData;
import com.ankoye.youngtao.gmc.model.response.GetCategoryResponse;
import com.ankoye.youngtao.gmc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 拉取目录，需要做个缓存
     * @return
     */
    @GetMapping("/get")
    public ResponseResult<GetCategoryResponse> getCategory() {
        List<CategoryData> categoryList = categoryService.getCategory();
        GetCategoryResponse response = new GetCategoryResponse();
        response.setCategoryList(categoryList);
        return ResponseResult.success(response);
    }

}
