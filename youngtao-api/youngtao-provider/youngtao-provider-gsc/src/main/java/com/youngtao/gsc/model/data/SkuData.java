package com.youngtao.gsc.model.data;

import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Data
public class SkuData implements Serializable {

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
