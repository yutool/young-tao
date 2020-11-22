package com.ankoye.youngtao.gmc.service;

import com.ankoye.youngtao.gmc.model.data.CategoryData;
import com.ankoye.youngtao.gmc.model.domain.CategoryDO;
import com.ankoye.youngtao.web.support.IService;

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
}
