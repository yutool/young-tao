package com.youngtao.omc.controller;

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

    @PostMapping("/add")
    public void addCart(@RequestBody AddCartRequest request) {
        String userId = "0";
        cartService.addCart(request, userId);
    }

    @GetMapping("/getUserCart")
    public List<CartResponse> getUserCart() {
        String userId = "0";
        return cartService.listByUserId(userId);
    }

}
