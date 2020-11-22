package com.ankoye.youngtao.gmc.model.domain;

import com.ankoye.youngtao.gmc.model.json.SkuTemplateJson;
import com.ankoye.youngtao.gmc.model.json.SpecJson;
import com.ankoye.youngtao.web.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

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
     * 价格范围
     */
    private List<BigDecimal> priceRange;

    /**
     * 商品详情说明
     */
    private String detail;

    /**
     * 商品服务
     */
    private List<String> serve;

    /**
     * 优惠券
     */
    private List<String> coupon;

    /**
     * 规格参数
     */
    private SpecJson spec;

    /**
     * sku模板
     */
    private SkuTemplateJson skuTemplate;

    /**
     * 运费
     */
    private BigDecimal fare;

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
     *  0-下架，1-上架
     */
    private Integer isMarketable;

    /**
     * 0-审核中，1-审核成功，2-审核失败
     */
    private Integer status;

}