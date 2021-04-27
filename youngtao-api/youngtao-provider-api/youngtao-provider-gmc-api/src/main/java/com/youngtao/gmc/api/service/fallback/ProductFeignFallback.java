package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.ProductFeign;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public class ProductFeignFallback implements ProductFeign {

    @Override
    public RpcResult<ProductDTO> getBySpuId(String spuIds) {
        return null;
    }

    @Override
    public RpcResult<SpuSkuDTO> getSpuSku(String skuId) {
        return null;
    }

    @Override
    public RpcResult<Boolean> paySuccess(List<UpdateStockArg> args) {
        return null;
    }
}
