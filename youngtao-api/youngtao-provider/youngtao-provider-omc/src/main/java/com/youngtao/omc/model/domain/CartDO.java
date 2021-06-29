package com.youngtao.omc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车，可直接存储到Redis
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
@TableName("omc_cart")
public class CartDO implements Serializable {
    private static final long serialVersionUID = 519088811468850687L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}