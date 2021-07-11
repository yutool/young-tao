package com.youngtao.gmc.controller;

import com.github.pagehelper.PageInfo;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.req.AddProductReq;
import com.youngtao.gmc.model.req.ConfirmOrderReq;
import com.youngtao.gmc.model.req.GetMerchantProductReq;
import com.youngtao.gmc.model.req.SearchProductReq;
import com.youngtao.gmc.model.res.ConfirmOrderRes;
import com.youngtao.gmc.service.ProductService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/search")
    public PageInfo<SpuSkuData> searchProduct(@RequestBody SearchProductReq request) {
        return productService.searchProduct(request);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody AddProductReq request) {
        productService.addProduct(request);
    }

    @GetMapping("/{id}")
    public ProductData getProduct(@PathVariable String id) {
        return productService.getBySpuId(id);
    }

    @PostMapping("/confirmOrder")
    public List<ConfirmOrderRes> confirmOrder(@RequestBody ConfirmOrderReq request) {
        return productService.confirmOrder(request);
    }

    @PostMapping("/getMerchantProduct")
    public PageInfo<ProductData> getMerchantProduct(@RequestBody GetMerchantProductReq request) {
        return productService.getMerchantProduct(request);
    }
}
