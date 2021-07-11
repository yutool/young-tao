package com.youngtao.gpc.model.res;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/28
 */
@Data
public class ConfirmOrderRes {

    private String merchantId;

    private String shopName;

    private BigDecimal postage;

    private List<Sku> skuList;

    @Data
    public static class Sku {
        private String skuId;

        private String spu;

        private JsonMap<String> sku;

        private BigDecimal price;

        private String image;

        private JsonList<String> serve;

        private Integer count;

        // 秒杀类型
        private Integer type = 1;
    }
}
