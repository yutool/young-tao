package com.youngtao.gpc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.fallback.GpcProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
@FeignClient(value = "youngtao-gsc-serve", contextId = "gsc-product", fallback = GpcProductFeignFallback.class)
public interface GpcProductFeign {

    @GetMapping("/api/gsc/product/listBySpuId")
    RpcResult<List<GpcSkuDTO>> listByIds(@RequestParam("skuIds") Collection<String> id);
}
