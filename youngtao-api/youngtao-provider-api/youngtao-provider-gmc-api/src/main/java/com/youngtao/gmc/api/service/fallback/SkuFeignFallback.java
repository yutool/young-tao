package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.SkuFeign;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
public class SkuFeignFallback implements SkuFeign {
    @Override
    public RpcResult<SkuDTO> getBySkuId(String skuId) {
        return null;
    }

    @Override
    public RpcResult<SpuSkuDTO> getSpuSku(String skuId) {
        return null;
    }

    @Override
    public RpcResult<List<SkuDTO>> listBySkuIds(Collection<String> skuIds) {
        return new RpcResult<>();
    }

    @Override
    public RpcResult<Boolean> batchFreezeScore(List<UpdateStockArg> args) {
        return null;
    }

    @Override
    public RpcResult<Boolean> batchUnfreezeScore(List<UpdateStockArg> args) {
        return null;
    }

    @Override
    public RpcResult<Boolean> batchDecreaseScore(List<UpdateStockArg> args) {
        return null;
    }
}
