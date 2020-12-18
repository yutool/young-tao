package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.FreezeInventoryArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.service.SkuFeign;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
public class SkuFeignFallback implements SkuFeign {
    @Override
    public RpcResult<List<SkuDTO>> listBySkuIds(List<String> skuIds) {
        return new RpcResult<>();
    }

    @Override
    public RpcResult<Boolean> batchFreezeInventory(List<FreezeInventoryArg> args) {
        return null;
    }
}
