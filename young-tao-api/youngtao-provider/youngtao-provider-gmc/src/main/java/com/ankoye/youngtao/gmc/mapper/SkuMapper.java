package com.ankoye.youngtao.gmc.mapper;

import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuDO> {

    int batchInset(Collection<SkuDO> skuList);

    List<SkuDO> listBySpuId(String spuId);

    List<SkuDO> listBySkuIds(Collection<String> skuIds);
}