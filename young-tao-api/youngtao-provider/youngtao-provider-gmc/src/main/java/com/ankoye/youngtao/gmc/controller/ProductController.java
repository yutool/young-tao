package com.ankoye.youngtao.gmc.controller;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.gmc.model.data.ProductData;
import com.ankoye.youngtao.gmc.model.request.AddProductRequest;
import com.ankoye.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseResult<Boolean> addProduct(@RequestBody AddProductRequest request) {
        Boolean result = productService.addProduct(request);
        return ResponseResult.success(result);
    }

    @GetMapping("/{id}")
    public ResponseResult<ProductData> getProduct(@PathVariable String id) {
        ProductData data = productService.getBySpuId(id);
        return ResponseResult.success(data);
    }

}
