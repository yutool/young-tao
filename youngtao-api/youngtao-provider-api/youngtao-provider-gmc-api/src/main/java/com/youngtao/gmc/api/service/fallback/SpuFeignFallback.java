package com.youngtao.gmc.api.service.fallback;

import com.youngtao.core.result.Result;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Component
public class SpuFeignFallback implements SpuFeign {

    @Override
    public Result<SpuDTO> getBySpuId(String spuId) {
        return null;
    }
}
