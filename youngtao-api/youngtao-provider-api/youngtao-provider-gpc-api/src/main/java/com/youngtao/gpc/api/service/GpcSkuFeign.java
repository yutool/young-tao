package com.youngtao.gpc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.fallback.GpcSkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
@FeignClient(value = "youngtao-gsc-serve", contextId = "gsc-sku", fallback = GpcSkuFeignFallback.class)
public interface GpcSkuFeign {
    @GetMapping("/api/gsc/sku/{menu}/{skuId}")
    RpcResult<GpcSkuDTO> getBySkuId(@PathVariable("menu") String menu, @PathVariable("skuId") String skuId);

}
