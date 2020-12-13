package com.youngtao.gmc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gmc.mapper.SpuMapper;
import com.youngtao.gmc.model.domain.SpuDO;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@RestController
public class SpuFeignClient implements SpuFeign {

    @Resource
    private SpuMapper spuMapper;

    @Override
    public RpcResult<SpuDTO> getBySpuId(String spuId) {
        SpuDO spuDO = spuMapper.selectBySpuId(spuId);
        SpuDTO spuDTO = BeanUtils.copy(spuDO, SpuDTO.class);
        return RpcResult.success(spuDTO);
    }

    @Override
    public RpcResult<List<SpuDTO>> listBySpuIds(Collection<String> spuIds) {
        List<SpuDO> spuDOList = spuMapper.listBySpuIds(spuIds);
        List<SpuDTO> spuDTOList = BeanUtils.copyList(spuDOList, SpuDTO.class);
        return RpcResult.success(spuDTOList);
    }
}
