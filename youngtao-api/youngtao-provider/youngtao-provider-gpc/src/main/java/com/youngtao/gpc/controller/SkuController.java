package com.youngtao.gpc.controller;

import com.youngtao.core.data.IdArg;
import com.youngtao.gpc.model.req.AddOrUpdateSkuReq;
import com.youngtao.gpc.service.SkuService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@ResponseWrapper
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/addOrUpdate")
    public void addOrUpdate(@RequestBody AddOrUpdateSkuReq request) {
        skuService.addOrUpdate(request);
    }

    @PostMapping("/deleteById")
    public void deleteById(@RequestBody IdArg arg) {
        skuService.deleteBySkuId(arg.getId());
    }

}
