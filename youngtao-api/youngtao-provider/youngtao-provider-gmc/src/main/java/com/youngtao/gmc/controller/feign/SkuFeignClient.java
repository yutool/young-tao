package com.youngtao.gmc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.api.model.arg.FreezeInventoryArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.query.FreezeInventoryQuery;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
@RestController
public class SkuFeignClient implements SkuFeign {
    @Resource
    private SkuMapper skuMapper;

    @Override
    public RpcResult<SkuDTO> getBySkuId(String skuId) {
        SkuDO skuDO = skuMapper.selectBySkuId(skuId);
        SkuDTO skuDTO = BeanUtils.copy(skuDO, SkuDTO.class);
        return RpcResult.success(skuDTO);
    }

    @Override
    public RpcResult<List<SkuDTO>> listBySkuIds(Collection<String> skuIds) {
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(skuIds);
        List<SkuDTO> skuDTOList = BeanUtils.copyList(skuDOList, SkuDTO.class);
        return RpcResult.success(skuDTOList);
    }

    @Override
    public RpcResult<Boolean> batchFreezeInventory(List<FreezeInventoryArg> args) {
        List<FreezeInventoryQuery> query = BeanUtils.copyList(args, FreezeInventoryQuery.class);
        boolean result = skuMapper.batchFreezeInventory(query) > 0;
        return RpcResult.success(result);
    }
}
