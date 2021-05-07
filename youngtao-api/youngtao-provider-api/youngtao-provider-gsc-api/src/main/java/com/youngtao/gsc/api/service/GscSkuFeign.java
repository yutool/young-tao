package com.youngtao.gsc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.fallback.GscSkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
@FeignClient(value = "youngtao-gsc-serve", contextId = "gsc-sku", fallback = GscSkuFeignFallback.class)
public interface GscSkuFeign {
    @GetMapping("/api/gsc/sku/{menu}/{skuId}")
    RpcResult<GscSkuDTO> getBySkuId(@PathVariable("menu") String menu, @PathVariable("skuId") String skuId);

}
