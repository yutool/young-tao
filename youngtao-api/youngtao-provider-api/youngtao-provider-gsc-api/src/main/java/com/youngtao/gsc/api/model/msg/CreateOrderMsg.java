package com.youngtao.gsc.api.model.msg;

import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@Data
public class CreateOrderMsg {
    private String userId;

    private String paymentId;

    private String remark;

    private String skuId;

    private String spuId;

    private JsonMap<String> sku;

    private String image;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private String shippingAddressId;
}
