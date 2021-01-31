package com.youngtao.opc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@Mapper
public interface OrderPayRecordMapper extends BaseMapper<OrderPayRecordDO> {

    int save(OrderPayRecordDO orderPayRecordDO);

    OrderPayRecordDO selectByPaymentId(String id);
}