package com.youngtao.gmc.model.domain;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.web.support.BaseEntity;
import com.youngtao.web.typehandler.JsonListTypeHandler;
import com.youngtao.web.typehandler.JsonMapTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品SPU实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("gmc_spu")
public class SpuDO extends BaseEntity implements Serializable {

    private String spuId;

    /**
     * 品牌+型号
     */
    private String spu;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 一级分类
     */
    private String category1Id;

    /**
     * 二级分类
     */
    private String category2Id;

    /**
     * 三级分类
     */
    private String category3Id;

    /**
     * 封面
     */
    private String cover;

    /**
     * 商品展示
     */
    private JsonList<String> images;

    /**
     * 价格范围
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<BigDecimal> priceRange;

    /**
     * 商品详情说明
     */
    private String detail;

    /**
     * 商品服务
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<String> serve;

    /**
     * 优惠券
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<String> coupon;

    /**
     * 规格参数
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<String> spec;

    /**
     * sku模板
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<List<String>> skuTemplate;

    /**
     * 是否包邮
     */
    private Boolean isFreeShipping;

    /**
     * 销售量
     */
    private Integer saleNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 排序
     */
    private Integer seq;

    /**
     *  是否可销售
     */
    private Boolean isMarketable;

    /**
     * 0-审核中，1-审核成功，2-审核失败
     */
    private Integer status;

}