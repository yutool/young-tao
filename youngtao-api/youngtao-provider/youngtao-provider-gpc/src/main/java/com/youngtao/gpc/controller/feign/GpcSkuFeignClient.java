package com.youngtao.gpc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcSkuFeign;
import com.youngtao.gpc.common.constant.RedisKey;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.web.cache.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
@RestController
public class GpcSkuFeignClient implements GpcSkuFeign {

    @Autowired
    private RedisManager<String> redisManager;

    @Override
    public RpcResult<GpcSkuDTO> getBySkuId(String menu, String skuId) {
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        GpcSkuDTO skuDTO = BeanUtils.copy(skuData, GpcSkuDTO.class);
        return RpcResult.success(skuDTO);
    }
}
