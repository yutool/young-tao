package com.youngtao.gmc.service;

import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.model.domain.CategoryDO;
import com.youngtao.web.support.IService;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
public interface CategoryService extends IService<CategoryDO> {

    /**
     * 获取目录
     * @return Tree directory
     */
    List<CategoryData> getCategory();

    /**
     * 获取一级目录
     */
    List<CategoryData> getMenu();

    /**
     * 获取子目录
     * @return
     */
    List<CategoryData> getSubmenu(String pid);
}
