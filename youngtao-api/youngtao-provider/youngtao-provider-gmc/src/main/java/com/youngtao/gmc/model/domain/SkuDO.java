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

/**
 * 商品SKU实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
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