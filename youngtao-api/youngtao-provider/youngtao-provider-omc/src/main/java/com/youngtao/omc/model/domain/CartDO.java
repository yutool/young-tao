package com.youngtao.omc.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.web.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车，可直接存储到Redis
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("omc_cart")
public class CartDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 519088811468850687L;

    /**
     * 需要对比是否降价
     */
    private String skuId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * spu title
     */
    private String spu;

    /**
     * 加入时单价
     */
    private BigDecimal oldPrice;

    /**
     * 数量
     */
    private Integer num;
}