package com.youngtao.gmc.model.data;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.gmc.common.constant.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Data
public class ProductData {

    private String spuId;

    /**
     * 品牌+型号
     */
    private String spu;

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
    private JsonList<BigDecimal> priceRange;

    /**
     * 商品详情说明
     */
    private String detail;

    /**
     * 商品服务
     */
    private JsonList<String> serve;

    /**
     * 优惠券
     */
    private JsonList<String> coupon;

    /**
     * 规格参数
     */
    private JsonMap<String> spec;

    /**
     * sku模板
     */
    private JsonMap<List<String>> skuTemplate;

    /**
     * 是否包邮
     */
    private BigDecimal postage;

    /**
     * 销售量
     */
    private Integer saleNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     *  是否可销售
     */
    private Boolean isMarketable;

    /**
     * 0-审核中，1-审核成功，2-审核失败
     */
    private Integer status;

    /**
     * sku
     */
    private List<Sku> skuList;

    @Data
    public static class Sku {

        private String skuId;

        private String spuId;

        private String title;

        private JsonMap<String> sku;

        private JsonList<String> images;

        private BigDecimal price;

        private BigDecimal oldPrice;

        private Integer num;

        private Integer alertNum;

        private Integer saleNum;

        private Integer freezeNum;

        private Boolean defaultShow;

        /**
         * 商品类型
         */
        private Integer type = ProductType.ORDINARY;
    }
}
