package com.youngtao.omc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.web.typehandler.JsonMapTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单商品实体类
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Data
@TableName("omc_order_item")
public class OrderItemDO implements Serializable {
    private static final long serialVersionUID = 834445604095061529L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

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
    private String spu;

    /**
     * sku Json
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<String> sku;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品单价
     */
    private BigDecimal oldPrice;

    /**
     * 商品实付价格
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

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}