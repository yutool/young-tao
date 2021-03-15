package com.youngtao.gsc.api.model.dto;

import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
@Data
public class GscSkuDTO implements Serializable {

    private String skuId;

    private String spuId;

    private String title;

    private JsonMap<String> sku;

    private String image;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private Date startTime;

    private Date endTime;

    private Integer num;

    private Integer residue;

}
