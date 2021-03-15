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
    public RpcResult<List<GscSkuDTO>> listBySpuId(String id) {
        List<GscSkuDTO> result = Lists.newArrayList();
        for (String menu : DateUtils.getDateMenus()) {
            List<SkuData> skuDataList = redisManager.hashValues(RedisKey.SKU_INFO_KEY.format(menu, id));
            result.addAll(skuConvert.toSkuDTO(skuDataList));
        }
        return RpcResult.success(result);
    }
}
