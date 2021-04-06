package com.youngtao.gmc.service;

import com.youngtao.gmc.model.data.RecommendProductData;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
public interface RecommendService {

    /**
     * 获取推荐列表
     * @return
     */
    List<RecommendProductData> getProduct();
}
