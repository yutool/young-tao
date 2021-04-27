package com.youngtao.gmc.controller;

import com.youngtao.core.data.DeleteArg;
import com.youngtao.core.data.IdArg;
import com.youngtao.gmc.model.request.UpdateSpuRequest;
import com.youngtao.gmc.service.SpuService;
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
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @PostMapping("/update")
    public void update(@RequestBody UpdateSpuRequest request) {
        spuService.update(request);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteArg arg) {
        spuService.deleteBySpuId(arg);
    }

    @PostMapping("/recycle")
    public void recycle(@RequestBody IdArg arg) {
        spuService.recycleBySpuId(arg.getId());
    }
}
