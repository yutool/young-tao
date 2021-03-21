package com.youngtao.gsc.controller.feign;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.GscProductFeign;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.model.convert.SkuConvert;
import com.youngtao.gsc.model.data.SkuData;
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
public class GscProductFeignClient implements GscProductFeign {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private SkuConvert skuConvert;

    @Override
    public RpcResult<List<GscSkuDTO>> listByIds(Collection<String> skuIds) {
        List<GscSkuDTO> result = Lists.newArrayList();
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
