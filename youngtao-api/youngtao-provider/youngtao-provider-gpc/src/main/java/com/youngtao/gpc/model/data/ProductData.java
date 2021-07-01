package com.youngtao.gpc.model.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Data
public class ProductData {

    private String spuId;

    private String spu;

    private String merchantId;

    private String shopName;

    private String brandId;

    private String brandName;

    private String category1Id;

    private String category2Id;

    private String category3Id;

    private JsonList<String> images;

    private String detail;

    private JsonList<String> serve;

    private JsonMap<String> spec;

    private JsonMap<List<String>> skuTemplate;

    private BigDecimal postage;

    private Integer saleNum;

    private Integer commentNum;

    private Integer seq;

    private Boolean isMarketable;

    private Integer status;

    private List<Sku> skuList;

    @Data
    public static class Sku {
        private String skuId;

        private String spuId;

        private JsonMap<String> sku;

        private String image;

        private BigDecimal oldPrice;

        private BigDecimal price;

        @JsonFormat(pattern = "yyyy-MM-dd HH")
        private Date startTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH")
        private Date endTime;

        private Integer num;

        private Integer residue;

        private Boolean isMarketable;

        private Integer status;

        private Date checkTime;
    }
}
