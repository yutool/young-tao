package com.youngtao.opc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录表 实体类
 *
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@Data
@TableName("opc_order_pay_record")
public class OrderPayRecordDO implements Serializable {
    private static final long serialVersionUID = 113755885319857971L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 支付id
     */
    private String paymentId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 0-待支付 1-完成 1-失败
     */
    private Integer status;

    /**
     * 1-支付宝 2-微信 3-他人代付
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付流水号
     */
    private String transactionId;

    /**
     * 备注
     */
    private String remark;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}