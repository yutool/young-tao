package com.youngtao.gsc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.fallback.GscProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
@FeignClient(value = "youngtao-gsc-serve", contextId = "product", fallback = GscProductFeignFallback.class)
public interface GscProductFeign {

    @GetMapping("/api/gsc/product/listBySpuId")
    RpcResult<List<GscSkuDTO>> listByIds(@RequestParam("skuIds") Collection<String> id);
}
