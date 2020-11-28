package com.ankoye.youngtao.gmc.model.response;

import com.ankoye.youngtao.core.lang.JsonList;
import com.ankoye.youngtao.core.lang.JsonMap;
import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.google.common.collect.Lists;
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

    private String spuId;

    private String title;

    private String merchantId;

    private String merchantName;

    private String brandName;

    private JsonList<String> serve;

    private Boolean isFreeShipping;

    private List<Sku> skuLIst;

    @Data
    public static class Sku {
        private String skuId;

        private JsonMap<String> sku;

        private BigDecimal price;

        private Integer count;
    }

    public static List<ConfirmOrderResponse> copyBy(List<SpuDO> spuDOS, Map<String, List<SkuDO>> skuDOMap, Map<String, Integer> countMap) {
        List<ConfirmOrderResponse> dataList = Lists.newArrayList();
        for (SpuDO spuDO : spuDOS) {
            ConfirmOrderResponse data = new ConfirmOrderResponse();
            data.setSpuId(spuDO.getSpuId());
            data.setTitle(spuDO.getTitle());
            data.setMerchantId(spuDO.getMerchantId());
            data.setMerchantName(spuDO.getMerchantId());
            data.setBrandName(spuDO.getBrandName());
            data.setServe(spuDO.getServe());
            data.setIsFreeShipping(spuDO.getIsFreeShipping());

            List<Sku> skuList = Lists.newArrayList();
            for (SkuDO skuDO : skuDOMap.get(spuDO.getSpuId())) {
                Sku sku = new Sku();
                sku.setSkuId(skuDO.getSkuId());
                sku.setSku(skuDO.getSku());
                sku.setPrice(skuDO.getPrice());
                sku.setCount(countMap.get(sku.getSkuId()));
                skuList.add(sku);
            }
            data.setSkuLIst(skuList);
            dataList.add(data);
        }
        return dataList;
    }
}
