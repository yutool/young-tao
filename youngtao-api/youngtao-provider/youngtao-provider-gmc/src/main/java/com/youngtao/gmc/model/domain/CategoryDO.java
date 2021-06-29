package com.youngtao.gmc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类实体类
 *
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Data
@TableName("gmc_category")
public class CategoryDO implements Serializable {
    private static final long serialVersionUID = -47219282192887378L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 上级分类id
     */
    private String parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接
     */
    private String url;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 是否导航
     */
    private Integer isMenu;

    /**
     * sku模板
     */
    private Object template;

    /**
     * 排序
     */
    private Integer seq;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}