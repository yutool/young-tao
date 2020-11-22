package com.ankoye.youngtao.gmc.model.request;

import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.ankoye.youngtao.gmc.model.json.SkuJson;
import com.ankoye.youngtao.gmc.model.json.SkuTemplateJson;
import com.ankoye.youngtao.gmc.model.json.SpecJson;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.CollectionUtils;

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
    private List<String> priceRange;

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
     * sku
     */
    private List<Sku> skuList;

    @Data
    public static class Sku {

        /**
         * 商品标题
         */
        private String title;

        /**
         * 商品规格
         */
        private SkuJson sku;

        /**
         * 商品图片
         */
        private List<String> images;

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
         * 默认显示
         */
        private Integer defaultShow;
    }

    public SpuDO convertToSpu() {
        return SpuDO.builder()
                .spu(spu)
                .title(title)
                .merchantId(merchantId)
                .brandId(brandId)
                .brandName(brandName)
                .category1Id(category1Id)
                .category2Id(category2Id)
                .category3Id(category3Id)
                .cover(cover)
                .priceRange(priceRange)
                .detail(detail)
                .serve(serve)
                .coupon(coupon)
                .spec(spec)
                .skuTemplate(skuTemplate)
                .fare(fare)
                .build();
    }

    public List<SkuDO> convertToSkuList() {
        List<SkuDO> skuDOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(skuList)) {
            for (Sku sku : skuList) {
                SkuDO skuDO = SkuDO.builder()
                        .title(sku.getTitle())
                        .sku(sku.getSku())
                        .images(sku.getImages())
                        .price(sku.getPrice())
                        .discount(sku.getDiscount())
                        .num(sku.getNum())
                        .alertNum(sku.getAlertNum())
                        .defaultShow(sku.getDefaultShow())
                        .build();
                skuDOList.add(skuDO);
            }
        }
        return skuDOList;
    }

}
