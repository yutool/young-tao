package com.youngtao.gmc.controller;

import com.youngtao.core.data.IdArg;
import com.youngtao.gmc.model.request.UpdateSkuRequest;
import com.youngtao.gmc.service.SkuService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/update")
    public void update(@RequestBody UpdateSkuRequest request) {
        skuService.update(request);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody IdArg idArg) {
        skuService.deleteBySkuId(idArg.getId());
    }

}
