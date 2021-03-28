package com.youngtao.omc.service;

import com.youngtao.omc.model.request.AddCartRequest;
import com.youngtao.omc.model.response.CartResponse;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
public interface CartService {
    /**
     * 添加购物车
     */
    void addCart(AddCartRequest request, String userId);

    /**
     * 获取用户购物车
     * @param userId userId
     */
    List<CartResponse> listByUserId(String userId);
}
