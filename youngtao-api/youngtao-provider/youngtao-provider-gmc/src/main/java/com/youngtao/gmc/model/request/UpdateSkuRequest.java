package com.youngtao.gmc.model.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Data
public class UpdateSkuRequest {
    private String skuId;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private Integer num;

    private Integer alertNum;
}
