package com.youngtao.gsc.controller;

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

    @GetMapping("/listByTime/{time}")
    public ResponseResult<List<SkuData>> listByTime(@PathVariable String time) {
        List<SkuData> response = productService.listByTime(time);
        return ResponseResult.success(response);
    }

    @GetMapping("/detail/{skuId}")
    public ResponseResult<ProductData> detail(@PathVariable String skuId) {
        ProductData response = productService.detail(skuId);
        return ResponseResult.success(response);
    }
}
