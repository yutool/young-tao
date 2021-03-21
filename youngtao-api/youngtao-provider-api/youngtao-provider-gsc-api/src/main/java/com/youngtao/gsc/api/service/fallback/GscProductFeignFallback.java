package com.youngtao.gsc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.service.GscProductFeign;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
public class GscProductFeignFallback implements GscProductFeign {
    @Override
    public RpcResult<List<GscSkuDTO>> listByIds(Collection<String> id) {
        return null;
    }
}
