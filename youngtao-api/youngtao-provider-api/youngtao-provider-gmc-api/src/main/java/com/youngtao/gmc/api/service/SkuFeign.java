package com.youngtao.gmc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.service.fallback.SkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@FeignClient(value = "youngtao-gmc-serve", contextId = "sku", fallback = SkuFeignFallback.class)
public interface SkuFeign {

    @GetMapping("/api/gmc/sku/{skuId}")
    RpcResult<SkuDTO> getBySkuId(@PathVariable("skuId") String skuId);

    /**
     * 根据skuList获取sku
     * @param skuIds list
     * @return sku
     */
    @PostMapping("/api/gmc/sku/listBySkuIds")
    RpcResult<List<SkuDTO>> listBySkuIds(@RequestParam("skuIds") Collection<String> skuIds);

    /**
     * 批量冻结库存
     * @param args args
     * @return bool
     */
    @PostMapping("/api/gmc/sku/batchFreezeScore")
    RpcResult<Boolean> batchFreezeScore(@RequestBody List<UpdateStockArg> args);

    /**
     * 批量冻结库存
     * @param args args
     * @return bool
     */
    @PostMapping("/api/gmc/sku/batchUnfreezeScore")
    RpcResult<Boolean> batchUnfreezeScore(@RequestBody List<UpdateStockArg> args);

    /**
     * 批量冻结库存
     * @param args args
     * @return bool
     */
    @PostMapping("/api/gmc/sku/batchDecreaseScore")
    RpcResult<Boolean> batchDecreaseScore(@RequestBody List<UpdateStockArg> args);
}
