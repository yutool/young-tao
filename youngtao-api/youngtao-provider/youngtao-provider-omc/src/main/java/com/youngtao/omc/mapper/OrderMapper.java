package com.youngtao.omc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.omc.model.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    Integer batchInsert(List<OrderDO> orderList);
}