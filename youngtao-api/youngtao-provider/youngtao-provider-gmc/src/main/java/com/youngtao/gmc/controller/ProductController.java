package com.youngtao.gmc.controller;

import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.request.AddProductRequest;
import com.youngtao.gmc.model.request.ConfirmOrderRequest;
import com.youngtao.gmc.model.response.ConfirmOrderResponse;
import com.youngtao.gmc.service.ProductService;
import com.youngtao.web.model.PageArg;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@RestController
@ResponseWrapper
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Boolean addProduct(@RequestBody AddProductRequest request) {
        return productService.addProduct(request);
    }

    @GetMapping("/{id}")
    public ProductData getProduct(@PathVariable String id) {
        return productService.getBySpuId(id);
    }

    @PostMapping("/confirmOrder")
    public List<ConfirmOrderResponse> confirmOrder(@RequestBody ConfirmOrderRequest request) {
        return productService.confirmOrder(request);
    }

    @PostMapping("/listRecommendProduct")
    public List<SpuSkuData> listRecommendProduct(@RequestBody @Valid PageArg arg) {
        return productService.listRecommendProduct(arg);
    }

}
