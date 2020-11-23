package com.ankoye.youngtao.gmc.mapper;

import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuDO> {

    int batchInset(List<SkuDO> skuList);

    List<SkuDO> listBySpuId(String spuId);
}