package com.youngtao.gpc.model.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Data
public class AddOrUpdateSkuReq {
    private String skuId;

    private BigDecimal price;

    private Date date;

    private Integer hours;

    private Integer num;
}
