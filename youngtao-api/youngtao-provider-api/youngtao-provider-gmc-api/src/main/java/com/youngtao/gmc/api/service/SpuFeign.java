package com.youngtao.gmc.api.service;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.fallback.SpuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@FeignClient(value = "youngtao-gmc-serve", contextId = "spu", fallback = SpuFeignFallback.class)
public interface SpuFeign {

    /**
     * 根据spuId获取spu
     * @param spuId spuId
     * @return record
     */
    @GetMapping("/api/gmc/spu/{spuId}")
    RpcResult<SpuDTO> getBySpuId(@PathVariable("spuId") String spuId);

    /**
     * 根据spuId获取spu
     * @param spuIds list
     * @return list
     */
    @GetMapping("/api/gmc/spu/listBySpuIds")
    RpcResult<List<SpuDTO>> listBySpuIds(@RequestParam("spuIds") Collection<String> spuIds);
}
