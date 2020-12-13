package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Component
public class SpuFeignFallback implements SpuFeign {

    @Override
    public RpcResult<SpuDTO> getBySpuId(String spuId) {
        return null;
    }

    @Override
    public RpcResult<List<SpuDTO>> listBySpuIds(Collection<String> spuIds) {
        return null;
    }
}
