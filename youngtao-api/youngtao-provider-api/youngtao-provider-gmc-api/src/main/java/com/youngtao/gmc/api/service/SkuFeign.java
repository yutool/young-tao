package com.youngtao.gmc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.FreezeInventoryArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.service.fallback.SkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@FeignClient(value = "youngtao-gmc-serve", contextId = "sku", fallback = SkuFeignFallback.class)
public interface SkuFeign {

    /**
     * 根据skuList获取sku
     * @param skuIds list
     * @return sku
     */
    @PostMapping("/api/gmc/sku/listBySkuIds")
    RpcResult<List<SkuDTO>> listBySkuIds(@RequestParam("skuIds") List<String> skuIds);

    /**
     * 批量冻结库存
     * @param args args
     * @return bool
     */
    @PostMapping("/api/gmc/sku/batchFreezeInventory")
    RpcResult<Boolean> batchFreezeInventory(@RequestParam("skus") List<FreezeInventoryArg> args);
}
