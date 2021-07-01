package com.youngtao.gpc.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.gpc.model.data.ProductData;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.gpc.model.request.ConfirmOrderRequest;
import com.youngtao.gpc.model.request.GetMerchantProductRequest;
import com.youngtao.gpc.model.response.ConfirmOrderResponse;
import com.youngtao.gpc.model.response.GetSeckillPageResponse;
import com.youngtao.gpc.service.ProductService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@RestController
@ResponseWrapper
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/timeMenu")
    public List<String> getTimeMenu() {
        return DateUtils.getDateMenus();
    }

    @GetMapping("/getSeckillPage")
    public GetSeckillPageResponse getSeckillPage() {
        return productService.getSeckillPage();
    }

    @GetMapping("/listByTime/{idx}/{page}/{size}")
    public Set<SkuData> listByTime(@PathVariable Integer idx, @PathVariable Integer page, @PathVariable Integer size) {
        Preconditions.checkArgument(page > 0, "The page cannot be less than 0");
        Preconditions.checkArgument(size >= 0 && size <= 100, "The size must be between 0 and 100");
        Preconditions.checkArgument(idx >= 0 && idx < DateUtils.MENU_SIZE, "The idx must be between 0 and 6");
        return productService.listByTime(DateUtils.getDateMenus().get(idx), page, size);
    }

    @GetMapping("/detail/{time}/{skuId}")
    public ProductData detail(@PathVariable String time, @PathVariable String skuId) {
        return productService.detail(time, skuId);
    }

    @PostMapping("/confirmOrder")
    public ConfirmOrderResponse confirmOrder(@Valid @RequestBody ConfirmOrderRequest request) {
        return productService.confirmOrder(request);
    }

    @PostMapping("/getMerchantProduct")
    public PageInfo<ProductData> getMerchantProduct(@RequestBody GetMerchantProductRequest request) {
        return productService.getMerchantProduct(request);
    }

}
