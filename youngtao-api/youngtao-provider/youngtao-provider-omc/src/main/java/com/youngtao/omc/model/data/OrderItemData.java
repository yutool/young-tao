package com.youngtao.omc.model.data;

import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class OrderItemData {
    private String spuId;

    private String skuId;

    private String spu;

    private JsonMap<String> sku;

    private String image;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private Integer num;

    private BigDecimal totalPrice;
}
