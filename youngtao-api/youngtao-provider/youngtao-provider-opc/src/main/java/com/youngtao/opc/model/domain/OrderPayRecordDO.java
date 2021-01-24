package com.youngtao.opc.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.web.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 支付记录表 实体类
 *
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("opc_order_pay_record")
public class OrderPayRecordDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 113755885319857971L;

    /**
     * 支付id
     */
    private String paymentId;

    /**
     * 1-支付宝 2-微信 3-他人代付
     */
    private Integer payType;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0-待支付 1-完成 1-失败
     */
    private Integer status;


    /**
     * 支付流水号
     */
    private String transactionId;

    /**
     * 支付时间
     */
    private Date payTime;

}