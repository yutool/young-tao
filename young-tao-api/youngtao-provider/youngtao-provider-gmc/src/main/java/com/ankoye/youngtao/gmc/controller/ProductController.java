package com.ankoye.youngtao.gmc.controller;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.gmc.model.request.AddProductRequest;
import com.ankoye.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseResult<Boolean> addProduct(@RequestBody AddProductRequest request) {
        Boolean result = productService.addProduct(request);
        return ResponseResult.success(result);
    }
}
