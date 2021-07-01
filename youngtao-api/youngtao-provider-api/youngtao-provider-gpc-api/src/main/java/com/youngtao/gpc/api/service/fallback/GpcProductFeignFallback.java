package com.youngtao.gpc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcProductFeign;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
public class GpcProductFeignFallback implements GpcProductFeign {
    @Override
    public RpcResult<List<GpcSkuDTO>> listByIds(Collection<String> id) {
        return null;
    }
}
