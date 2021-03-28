package com.youngtao.omc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.omc.model.domain.OrderItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemDO> {

    List<OrderItemDO> selectByOrderIds(Collection<String> orderIds);

    Integer batchInsert(List<OrderItemDO> orderItemList);
}