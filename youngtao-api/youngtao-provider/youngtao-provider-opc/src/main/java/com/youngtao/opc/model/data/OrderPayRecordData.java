package com.youngtao.opc.model.data;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@Data
public class OrderPayRecordData implements Serializable {

    /**
     * 支付id
     */
    private String paymentId;

    /**
     * 用户id
     */
    private String userId;

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
     * 0-完成 1-失败
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
