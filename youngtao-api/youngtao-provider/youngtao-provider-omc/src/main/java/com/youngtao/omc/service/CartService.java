package com.youngtao.omc.service;

import com.youngtao.omc.model.request.AddCartRequest;
import com.youngtao.omc.model.request.UpdateNumRequest;
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
    void addCart(AddCartRequest request);

    /**
     * 获取用户购物车
     * @param userId userId
     */
    List<CartResponse> listByUserId(String userId);

    /**
     * 更新数量
     * @param request
     */
    void updateNum(UpdateNumRequest request);

    /**
     * 删除购物车
     * @param id id
     */
    void deleteCart(String id);

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<String> ids);
}
