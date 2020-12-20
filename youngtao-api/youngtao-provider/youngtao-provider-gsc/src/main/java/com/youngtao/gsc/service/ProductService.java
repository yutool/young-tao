package com.youngtao.gsc.service;

import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public interface ProductService {

    /**
     * 获取当前秒杀时间段的商品
     * @param time time
     * @return list
     */
    List<SkuData> listByTime(String time);

    /**
     * 获取秒杀商品详情
     * @param skuId id
     * @return record
     */
    ProductData detail(String skuId);
}
