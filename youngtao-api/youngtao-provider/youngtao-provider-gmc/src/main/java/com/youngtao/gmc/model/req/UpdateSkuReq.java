package com.youngtao.gmc.model.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Data
public class UpdateSkuReq {
    private String skuId;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private Integer num;

    private Integer alertNum;
}
