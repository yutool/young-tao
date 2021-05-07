package com.youngtao.gsc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.GscSkuFeign;

/**
 * @author ankoye@qq.com
 * @date 2021/05/05
 */
public class GscSkuFeignFallback implements GscSkuFeign {
    @Override
    public RpcResult<GscSkuDTO> getBySkuId(String menu, String skuId) {
        return null;
    }
}
