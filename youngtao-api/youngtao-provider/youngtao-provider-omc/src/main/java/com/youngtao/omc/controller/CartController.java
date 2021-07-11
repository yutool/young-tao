package com.youngtao.omc.controller;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.data.IdArg;
import com.youngtao.core.data.IdsArg;
import com.youngtao.omc.model.req.AddCartReq;
import com.youngtao.omc.model.req.UpdateNumReq;
import com.youngtao.omc.model.res.CartRes;
import com.youngtao.omc.service.CartService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<CartRes> getUserCart() {
        AuthInfo authInfo = AuthContext.get();
        return cartService.listByUserId(authInfo.getUserId());
    }

    @PostMapping("/add")
    public void addCart(@RequestBody AddCartReq request) {
        cartService.addCart(request);
    }

    @PostMapping("/updateNum")
    public void updateNum(@RequestBody @Valid UpdateNumReq request) {
        cartService.updateNum(request);
    }

    @PostMapping("/delete")
    public void deleteCart(@RequestBody IdArg arg) {
        cartService.deleteCart(arg.getId());
    }

    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody IdsArg arg) {
        cartService.batchDelete(arg.getIds());
    }
}
