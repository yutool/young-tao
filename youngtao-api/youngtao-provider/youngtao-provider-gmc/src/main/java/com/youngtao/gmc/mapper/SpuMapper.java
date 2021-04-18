package com.youngtao.gmc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.query.UpdateSaleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    int paySuccess(@Param("spuList") Collection<UpdateSaleQuery> query);

    List<SpuDO> getMerchantProduct(@Param("merchantId")  String merchantId);
}