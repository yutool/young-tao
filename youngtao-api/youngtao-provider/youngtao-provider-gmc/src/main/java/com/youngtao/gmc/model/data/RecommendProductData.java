package com.youngtao.gmc.model.data;

import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
@Data
public class RecommendProductData {

    private CategoryData category;

    private List<SpuSkuData> productList;
}
