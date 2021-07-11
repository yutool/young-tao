package com.youngtao.omc.service;

import com.youngtao.omc.model.req.AddCartReq;
import com.youngtao.omc.model.req.UpdateNumReq;
import com.youngtao.omc.model.res.CartRes;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
public interface CartService {
    /**
     * 添加购物车
     */
    void addCart(AddCartReq request);

    /**
     * 获取用户购物车
     * @param userId userId
     */
    List<CartRes> listByUserId(String userId);

    /**
     * 更新数量
     * @param request
     */
    void updateNum(UpdateNumReq request);

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
