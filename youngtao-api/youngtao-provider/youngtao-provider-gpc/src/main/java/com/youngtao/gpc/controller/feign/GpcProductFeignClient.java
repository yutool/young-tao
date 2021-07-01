package com.youngtao.gpc.controller.feign;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcProductFeign;
import com.youngtao.gpc.common.constant.RedisKey;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.gpc.model.convert.SkuConvert;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.web.cache.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
@RestController
public class GpcProductFeignClient implements GpcProductFeign {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private SkuConvert skuConvert;

    @Override
    public RpcResult<List<GpcSkuDTO>> listByIds(Collection<String> skuIds) {
        List<GpcSkuDTO> result = Lists.newArrayList();
        String menu = DateUtils.currentMenu();
        for (String skuId : skuIds) {
            SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
            if (skuData != null) {
                result.add(skuConvert.toSkuDTO(skuData));
            }
        }
        return RpcResult.success(result);
    }
}
