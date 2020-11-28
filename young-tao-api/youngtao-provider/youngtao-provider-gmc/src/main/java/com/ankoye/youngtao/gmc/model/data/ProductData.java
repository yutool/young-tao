package com.ankoye.youngtao.gmc.model.data;

import com.ankoye.youngtao.core.lang.JsonList;
import com.ankoye.youngtao.core.lang.JsonMap;
import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.ankoye.youngtao.web.typehandler.JsonListTypeHandler;
import com.ankoye.youngtao.web.typehandler.JsonMapTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Data
@Builder
@AllArgsConstructor
public class ProductData {

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
    @Builder
    @AllArgsConstructor
    public static class Sku {

        private String skuId;

        /**
         * spuId
         */
        private String spuId;

        /**
         * 商品标题
         */
        private String title;

        /**
         * 商品规格
         */
        @TableField(typeHandler = JsonMapTypeHandler.class)
        private JsonMap<String> sku;

        /**
         * 商品图片
         */
        @TableField(typeHandler = JsonListTypeHandler.class)
        private JsonList<String> images;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * 折扣
         */
        private Float discount;

        /**
         * 库存数量
         */
        private Integer num;

        /**
         * 库存预警数量
         */
        private Integer alertNum;

        /**
         * 销量
         */
        private Integer saleNum;

        /**
         * 冻结库存
         */
        private Integer freezeNum;

        /**
         * 默认显示
         */
        private Boolean defaultShow;
    }

    public static ProductData copyBy(SpuDO spuDO, List<SkuDO> skuDOList) {
        if (spuDO == null) {
            return null;
        }
        ProductData data = ProductData.builder()
                .spuId(spuDO.getSpuId())
                .spu(spuDO.getSpu())
                .title(spuDO.getTitle())
                .merchantId(spuDO.getMerchantId())
                .brandId(spuDO.getBrandId())
                .brandName(spuDO.getBrandName())
                .category1Id(spuDO.getCategory1Id())
                .category2Id(spuDO.getCategory2Id())
                .category3Id(spuDO.getCategory3Id())
                .cover(spuDO.getCover())
                .images(spuDO.getImages())
                .priceRange(spuDO.getPriceRange())
                .detail(spuDO.getDetail())
                .serve(spuDO.getServe())
                .coupon(spuDO.getCoupon())
                .spec(spuDO.getSpec())
                .skuTemplate(spuDO.getSkuTemplate())
                .isFreeShipping(spuDO.getIsFreeShipping())
                .saleNum(spuDO.getSaleNum())
                .commentNum(spuDO.getCommentNum())
                .isMarketable(spuDO.getIsMarketable())
                .status(spuDO.getStatus())
                .build();
        List<Sku> skuList = Lists.newLinkedList();
        for (SkuDO skuDO : skuDOList) {
            Sku sku = Sku.builder()
                    .skuId(skuDO.getSkuId())
                    .spuId(skuDO.getSpuId())
                    .title(skuDO.getTitle())
                    .sku(skuDO.getSku())
                    .images(skuDO.getImages())
                    .price(skuDO.getPrice())
                    .discount(skuDO.getDiscount())
                    .num(skuDO.getNum())
                    .alertNum(skuDO.getAlertNum())
                    .saleNum(skuDO.getSaleNum())
                    .freezeNum(skuDO.getFreezeNum())
                    .defaultShow(skuDO.getDefaultShow())
                    .build();
            skuList.add(sku);
        }
        data.setSkuList(skuList);
        return data;
    }
}
