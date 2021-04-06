package com.youngtao.gmc.controller;

import com.youngtao.gmc.model.data.RecommendProductData;
import com.youngtao.gmc.service.RecommendService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
@ResponseWrapper
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/product")
    public List<RecommendProductData> getProduct() {
        return recommendService.getProduct();
    }
}
