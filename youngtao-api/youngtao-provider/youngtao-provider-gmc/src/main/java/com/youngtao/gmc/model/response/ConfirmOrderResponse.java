package com.youngtao.gmc.model.response;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.gmc.model.domain.SkuDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2020/11/28
 */
@Data
public class ConfirmOrderResponse {

    private String merchantId;

    private String merchantName;

    private Boolean isFreeShipping;

    private List<Sku> skuList;

    @Data
    public static class Sku {
        private String skuId;

        private JsonMap<String> sku;

        private BigDecimal price;

        private JsonList<String> serve;

        private Integer count;
    }
}
