package com.youngtao.gmc.mapper;

import com.youngtao.gmc.model.domain.SpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SpuMapper extends BaseMapper<SpuDO> {

    SpuDO selectBySpuId(String spuId);

    List<SpuDO> listBySpuIds(Collection<String> spuIds);
}