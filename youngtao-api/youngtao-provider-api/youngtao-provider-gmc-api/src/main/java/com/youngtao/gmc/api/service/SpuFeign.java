package com.youngtao.gmc.api.service;

import com.youngtao.core.result.Result;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.fallback.SpuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@FeignClient(value = "youngtao-gmc-serve", fallback = SpuFeignFallback.class)
public interface SpuFeign {

    /**
     * 根据spuId获取spu
     * @param spuId spuId
     * @return spu
     */
    @GetMapping("/api/gmc/spu/{spuId}")
    Result<SpuDTO> getBySpuId(@PathVariable("spuId") String spuId);
}
