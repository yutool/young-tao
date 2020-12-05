package com.youngtao.gmc.controller;

import com.youngtao.core.result.ResponseResult;
import com.youngtao.gmc.model.convert.CategoryConvert;
import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.service.CategoryService;
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
    @Autowired
    private CategoryConvert categoryConvert;

    /**
     * 拉取目录，需要做个缓存
     * @return
     */
    @GetMapping("/get")
    public ResponseResult<List<CategoryData>> getCategory() {
        List<CategoryData> categoryList = categoryService.getCategory();
        return ResponseResult.success(categoryList);
    }

}
