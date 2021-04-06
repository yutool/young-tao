package com.youngtao.opc.api.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
@Data
public class OrderPayRecordDTO {

    private String paymentId;

    private String userId;

    private Integer payType;

    private BigDecimal money;

    private String remark;

    private Integer status;

    private Date updateTime;

    private Date createTime;
}
