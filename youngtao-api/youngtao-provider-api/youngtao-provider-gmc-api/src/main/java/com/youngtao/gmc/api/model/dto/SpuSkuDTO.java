package com.youngtao.gmc.api.model.dto;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/01/09
 */
@Data
public class SpuSkuDTO implements Serializable {

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
}
