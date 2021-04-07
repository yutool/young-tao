package com.youngtao.gmc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuDO> {

    Integer batchInset(Collection<SkuDO> skuList);

    SkuDO selectBySkuId(String skuId);

    List<SkuDO> listBySpuId(String spuId);

    List<SkuDO> listBySpuIds(Collection<String> spuIds);

    List<SkuDO> listBySkuIds(Collection<String> skuIds);

    /**
     * 冻结库存
     */
    int batchFreezeScore(@Param("skuList") Collection<UpdateStockQuery> args);

    /**
     * 解冻库存
     */
    int batchUnfreezeScore(@Param("skuList") Collection<UpdateStockQuery> args);

    /**
     * 删除库存
     */
    int batchDecreaseScore(@Param("skuList") Collection<UpdateStockQuery> args);


    List<SkuDO> listDefaultBySpuId(Collection<String> spuIds);

    int paySuccess(@Param("skuList") List<UpdateStockQuery> query);
}