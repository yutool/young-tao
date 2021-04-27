package com.youngtao.gmc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductService productService;

    @Override
    public RpcResult<SkuDTO> getBySkuId(String skuId) {
        SkuDO skuDO = skuMapper.selectBySkuId(skuId);
        SkuDTO skuDTO = BeanUtils.copy(skuDO, SkuDTO.class);
        return RpcResult.success(skuDTO);
    }

    @Override
    public RpcResult<SpuSkuDTO> getSpuSku(String skuId) {
        SpuSkuData spuSku = productService.getSpuSku(skuId);
        SpuSkuDTO spuSkuDTO = BeanUtils.copy(spuSku, SpuSkuDTO.class);
        return RpcResult.success(spuSkuDTO);
    }

    @Override
    public RpcResult<List<SkuDTO>> listBySkuIds(Collection<String> skuIds) {
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(skuIds);
        List<SkuDTO> skuDTOList = BeanUtils.copyList(skuDOList, SkuDTO.class);
        return RpcResult.success(skuDTOList);
    }

    @Override
    public RpcResult<Boolean> batchFreezeScore(List<UpdateStockArg> args) {
        List<UpdateStockQuery> query = BeanUtils.copyList(args, UpdateStockQuery.class);
        boolean result = skuMapper.batchFreezeScore(query) > 0;
        return RpcResult.success(result);
    }

    @Override
    public RpcResult<Boolean> batchUnfreezeScore(List<UpdateStockArg> args) {
        List<UpdateStockQuery> query = BeanUtils.copyList(args, UpdateStockQuery.class);
        boolean result = skuMapper.batchUnfreezeScore(query) > 0;
        return RpcResult.success(result);
    }

    @Override
    public RpcResult<Boolean> batchDecreaseScore(List<UpdateStockArg> args) {
        List<UpdateStockQuery> query = BeanUtils.copyList(args, UpdateStockQuery.class);
        boolean result = skuMapper.batchDecreaseScore(query) > 0;
        return RpcResult.success(result);
    }
}
