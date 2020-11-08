package com.ankoye.youngtao.gmc.controller;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.gmc.model.Test;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @PostMapping
    public ResponseResult<Test> get(@RequestBody Test test) {
        System.out.println(test.getName() + " " + test.getAge());
        test.setAge("19");
        return ResponseResult.success(test);
    }

    @GetMapping
    public ResponseResult<String> get() {
        return ResponseResult.success("hello");
    }
}
