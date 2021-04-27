package com.youngtao.gmc.model.data;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/01/09
 */
@Data
public class SpuSkuData {

    private String spuId;

    private String spu;

    private String title;

    private String merchantId;

    private String shopName;

    private String brandId;

    private String brandName;

    private String category1Id;

    private String category2Id;

    private String category3Id;

    private String detail;

    private JsonList<String> serve;

    private JsonMap<String> spec;

    private JsonMap<List<String>> skuTemplate;

    private BigDecimal postage;

    private Integer saleNum;

    private Integer commentNum;

    private String skuId;

    private JsonMap<String> sku;

    private JsonList<String> images;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Integer num;

    public void generateTitle() {
        if (this.sku != null && this.spu != null) {
            StringBuilder sb = new StringBuilder(title);
            this.sku.forEach((key, value) -> {
                sb.append(" ").append(key).append(" ").append(value);
            });
            this.title = sb.toString();
        }
    }
}
