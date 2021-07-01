package com.youngtao.gpc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.gpc.model.domain.SkuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuDO> {

    SkuDO getBySkuId(@Param("skuId") String skuId);

    List<SkuDO> loadSkuToRedis(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("skuIds") Collection<String> skuIds);

    List<SkuDO> listBySpuIds(Collection<String> spuIds);

    List<String> getAllSpuIdByMerchant(String merchantId);
}