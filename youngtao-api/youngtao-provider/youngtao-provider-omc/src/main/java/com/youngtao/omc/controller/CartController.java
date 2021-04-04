package com.youngtao.omc.controller;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.data.IdArg;
import com.youngtao.omc.model.request.AddCartRequest;
import com.youngtao.omc.model.response.CartResponse;
import com.youngtao.omc.service.CartService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@ResponseWrapper
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getUserCart")
    public List<CartResponse> getUserCart() {
        AuthInfo authInfo = AuthContext.get();
        return cartService.listByUserId(authInfo.getUserId());
    }

    @PostMapping("/add")
    public void addCart(@RequestBody AddCartRequest request) {
        AuthInfo authInfo = AuthContext.get();
        cartService.addCart(request, authInfo.getUserId());
    }

    @PostMapping("/delete")
    public void deleteCart(@RequestBody IdArg arg) {
        cartService.deleteCart(arg.getId());
    }

}
