package com.youngtao.omc.model.domain;

import com.youngtao.web.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品实体类
 *
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderItemDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 834445604095061529L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * spuId
     */
    private String spuId;

    /**
     * skuId
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String title;

    /**
     * sku Json
     */
    private Object sku;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品原价
     */
    private BigDecimal oldPrice;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

}