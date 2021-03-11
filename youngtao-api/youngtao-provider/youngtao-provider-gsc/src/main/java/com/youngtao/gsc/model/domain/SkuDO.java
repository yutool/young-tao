package com.youngtao.gsc.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.web.support.BaseEntity;
import com.youngtao.web.typehandler.JsonMapTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀sku实体类
 *
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("gsc_sku")
public class SkuDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 612145136697446022L;

    /**
     * sku id
     */
    private String skuId;

    /**
     * spu ID
     */
    private String spuId;

    /**
     * sku
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<String> sku;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 原价格
     */
    private BigDecimal oldPrice;

    /**
     * 秒杀价格
     */
    private BigDecimal price;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 秒杀商品数
     */
    private Integer num;

    /**
     * 剩余库存数
     */
    private Integer residue;

    /**
     * 是否开启秒杀
     */
    private Boolean isMarketable;

    /**
     * 审核状态，0未审核，1审核通过，2审核不通过
     */
    private Integer status;

    /**
     * 审核日期
     */
    private Date checkTime;
}