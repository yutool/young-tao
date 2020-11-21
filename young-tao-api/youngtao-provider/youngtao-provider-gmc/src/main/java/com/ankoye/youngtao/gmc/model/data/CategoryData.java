package com.ankoye.youngtao.gmc.model.data;

import com.ankoye.youngtao.gmc.model.entity.CategoryDO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Data
@Builder
public class CategoryData {

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接
     */
    private String url;

    /**
     * sku模板
     */
    private Object template;

    /**
     * 上级分类id
     */
    private String parentId;

    /**
     * 子目录
     */
    private List<CategoryData> children;

    public static CategoryData copyBy(CategoryDO category) {
        if (category == null) {
            return null;
        }
        return CategoryData.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .icon(category.getIcon())
                .url(category.getUrl())
                .parentId(category.getParentId())
                .template(category.getTemplate())
                .build();
    }
}
