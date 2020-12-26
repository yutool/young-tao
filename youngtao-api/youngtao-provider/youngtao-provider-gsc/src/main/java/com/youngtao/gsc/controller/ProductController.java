package com.youngtao.gsc.controller;

import com.google.common.base.Preconditions;
import com.youngtao.core.result.ResponseResult;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/timeMenu")
    public ResponseResult<List<String>> getTimeMenu() {
        List<String> dateMenus = DateUtils.getDateMenus();
        return ResponseResult.success(dateMenus);
    }

    @GetMapping("/listByTime/{time}/{page}/{size}")
    public ResponseResult<Set<SkuData>> listByTime(@PathVariable String time, @PathVariable Integer page, @PathVariable Integer size) {
        Preconditions.checkArgument(page > 0, "The page cannot be less than 0");
        Preconditions.checkArgument(size >= 0 && size <= 100, "The size must be between 0 and 100");
        Set<SkuData> response = productService.listByTime(time, page, size);
        return ResponseResult.success(response);
    }

    @GetMapping("/detail/{time}/{skuId}")
    public ResponseResult<ProductData> detail(@PathVariable String time, @PathVariable String skuId) {
        ProductData response = productService.detail(time, skuId);
        return ResponseResult.success(response);
    }
}
