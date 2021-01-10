package com.youngtao.gmc.model.request;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Data
public class AddProductRequest {

    /**
     * 品牌+型号
     */
    private String spu;

    /**
     * 商品标题
     */
//    private String title;

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
     * 邮费
     */
    private BigDecimal postage;

    /**
     *  是否可销售
     */
    private Boolean isMarketable;

    /**
     * sku
     */
    private List<Sku> skuList;

    @Data
    public static class Sku {

        /**
         * 商品规格
         */
        private JsonMap<String> sku;

        /**
         * 商品图片
         */
        private JsonList<String> images;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * 原价
         */
        private BigDecimal oldPrice;

        /**
         * 库存数量
         */
        private Integer num;

        /**
         * 库存预警数量
         */
        private Integer alertNum;

        /**
         * 默认显示
         */
        private Boolean defaultShow;
    }
}
