package com.youngtao.uac.controller;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.data.IdArg;
import com.youngtao.uac.model.data.ShippingAddressData;
import com.youngtao.uac.model.request.AddShippingAddressRequest;
import com.youngtao.uac.service.ShippingAddressService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@ResponseWrapper
@RestController
@RequestMapping("/shippingAddress")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping("/getUserAddress")
    public List<ShippingAddressData> getUserAddress() {
        AuthInfo authInfo = AuthContext.get();
        return shippingAddressService.getByUserId(authInfo.getUserId());
    }

    @PostMapping("/addOrUpdate")
    public void addOrUpdateAddress(@RequestBody AddShippingAddressRequest request) {
        AuthInfo authInfo = AuthContext.get();
        shippingAddressService.addOrUpdateAddress(request, authInfo.getUserId());
    }

    @PostMapping("/setDefault")
    public void setDefault(@RequestBody IdArg arg) {
        AuthInfo authInfo = AuthContext.get();
        shippingAddressService.setDefault(arg.getId(), authInfo.getUserId());
    }

    @PostMapping("/delete")
    public void addAddress(@RequestBody IdArg idArg) {
        shippingAddressService.deleteByAddrId(idArg.getId());
    }

}
