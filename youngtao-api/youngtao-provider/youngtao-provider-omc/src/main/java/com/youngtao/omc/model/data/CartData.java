package com.youngtao.omc.model.data;

import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class CartData {
    private Integer id;

    private String userId;

    private String spuId;

    private String spu;

    private String skuId;

    private JsonMap<String> sku;

    private String image;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Integer num;
}
