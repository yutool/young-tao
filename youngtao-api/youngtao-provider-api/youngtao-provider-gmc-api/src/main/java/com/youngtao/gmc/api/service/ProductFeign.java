package com.youngtao.gmc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.fallback.ProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@FeignClient(value = "youngtao-gmc-serve", contextId = "product", fallback = ProductFeignFallback.class)
public interface ProductFeign {

    /**
     * 根据spuId获取商品
     * @param id spuId
     * @return list
     */
    @GetMapping("/api/gmc/product/listBySpuIds/{id}")
    RpcResult<ProductDTO> getBySpuId(@PathVariable("id") String id);

    /**
     * 获取spuSku
     * @param skuId skuId
     */
    @GetMapping("/api/gmc/product/getSpuSku")
    RpcResult<SpuSkuDTO> getSpuSku(@RequestParam("skuId") String skuId);

    /**
     * 支付成功
     * @param args args
     * @return bool
     */
    @PostMapping("/api/gmc/product/paySuccess")
    RpcResult<Boolean> paySuccess(@RequestBody List<UpdateStockArg> args);
}
