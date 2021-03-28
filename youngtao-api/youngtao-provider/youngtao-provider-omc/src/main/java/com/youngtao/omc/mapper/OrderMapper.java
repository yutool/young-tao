package com.youngtao.omc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.omc.model.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    List<OrderDO> selectByUserIdAndStatus(@Param("userId") String userId, @Param("status") Integer status);

    int batchInsert(List<OrderDO> orderList);

    int updateStatus(@Param("paymentId") String paymentId, @Param("status") Integer status);
}