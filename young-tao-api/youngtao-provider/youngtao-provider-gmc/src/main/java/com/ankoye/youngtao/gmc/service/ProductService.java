package com.ankoye.youngtao.gmc.service;

import com.ankoye.youngtao.gmc.model.request.AddProductRequest;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public interface ProductService {

    /**
     * 添加商品
     * @param request goods
     * @return bool
     */
    Boolean addProduct(AddProductRequest request);
}
