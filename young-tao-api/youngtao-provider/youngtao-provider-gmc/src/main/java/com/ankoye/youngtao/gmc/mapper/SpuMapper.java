package com.ankoye.youngtao.gmc.mapper;

import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SpuMapper extends BaseMapper<SpuDO> {

    SpuDO selectBySpuId(String spuId);
}