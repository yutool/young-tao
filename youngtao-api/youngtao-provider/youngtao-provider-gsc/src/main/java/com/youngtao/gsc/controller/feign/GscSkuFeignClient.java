package com.youngtao.gsc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.GscSkuFeign;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.web.cache.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
@RestController
public class GscSkuFeignClient implements GscSkuFeign {

    @Autowired
    private RedisManager<String> redisManager;

    @Override
    public RpcResult<GscSkuDTO> getBySkuId(String menu, String skuId) {
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        GscSkuDTO skuDTO = BeanUtils.copy(skuData, GscSkuDTO.class);
        return RpcResult.success(skuDTO);
    }
}
