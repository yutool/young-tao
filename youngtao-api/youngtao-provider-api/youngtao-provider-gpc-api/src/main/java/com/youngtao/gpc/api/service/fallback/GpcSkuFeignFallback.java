package com.youngtao.gpc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcSkuFeign;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
public class GpcSkuFeignFallback implements GpcSkuFeign {
    @Override
    public RpcResult<GpcSkuDTO> getBySkuId(String menu, String skuId) {
        return null;
    }
}
