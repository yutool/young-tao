package com.youngtao.gmc.controller;

import com.youngtao.core.result.ResponseResult;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Boolean response = productService.addProduct(request);
        return ResponseResult.success(response);
    }

    @GetMapping("/{id}")
    public ResponseResult<ProductData> getProduct(@PathVariable String id) {
        ProductData response = productService.getBySpuId(id);
        return ResponseResult.success(response);
    }

    @PostMapping("/confirmOrder")
    public ResponseResult<List<ConfirmOrderResponse>> confirmOrder(@RequestBody ConfirmOrderRequest request) {
        List<ConfirmOrderResponse> response = productService.confirmOrder(request);
        return ResponseResult.success(response);
    }

}
