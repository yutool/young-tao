package com.youngtao.gmc.api.model.dto;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@Data
public class SkuDTO implements Serializable {

    private String skuId;

    /**
     * spuId
     */
    private String spuId;

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
