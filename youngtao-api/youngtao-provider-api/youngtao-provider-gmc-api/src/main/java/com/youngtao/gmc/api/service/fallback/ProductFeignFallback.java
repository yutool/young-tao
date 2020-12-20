package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.service.ProductFeign;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public class ProductFeignFallback implements ProductFeign {

    @Override
    public RpcResult<ProductDTO> getBySpuId(String spuIds) {
        return null;
    }
}
