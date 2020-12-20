package com.youngtao.gmc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.query.FreezeInventoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuDO> {

    Integer batchInset(Collection<SkuDO> skuList);

    List<SkuDO> listBySpuId(String spuId);

    List<SkuDO> listBySpuIds(Collection<String> spuId);

    List<SkuDO> listBySkuIds(Collection<String> skuIds);

    Integer batchFreezeInventory(Collection<FreezeInventoryQuery> args);
}