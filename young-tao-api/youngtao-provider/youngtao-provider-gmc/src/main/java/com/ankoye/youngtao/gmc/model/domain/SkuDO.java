package com.ankoye.youngtao.gmc.model.domain;

import com.ankoye.youngtao.gmc.model.json.SkuJson;
import com.ankoye.youngtao.web.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品SKU实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */
@Data
@Builder
@TableName("gmc_sku")
public class SkuDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 227889639103151085L;

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
    private Integer defaultShow;

}