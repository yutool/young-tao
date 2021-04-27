package com.youngtao.gmc.model.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Data
public class CategoryElem {

    /**
     * 分类id
     */
    private String value;

    /**
     * 分类名称
     */
    private String label;

    /**
     * 上级分类id
     */
    private String parentId;

    /**
     * 子目录
     */
    private List<CategoryElem> children = Lists.newArrayList();
}
