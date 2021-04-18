package com.youngtao.gmc.controller;

import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.service.CategoryService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@RestController
@ResponseWrapper
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 拉取目录，需要做个缓存
     */
    @GetMapping("/get")
    public List<CategoryData> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping("/menu")
    public List<CategoryData> getMenu() {
        return categoryService.getMenu();
    }

    @GetMapping("/getSubmenu/{pid}")
    public List<CategoryData> getSubmenu(@PathVariable String pid) {
        return categoryService.getSubmenu(pid);
    }
}
