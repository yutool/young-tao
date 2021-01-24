package com.youngtao.opc.model.convert;

import com.youngtao.opc.model.data.OrderPayRecordData;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import org.mapstruct.Mapper;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@Mapper(componentModel = "spring")
public interface OrderPayRecordConvert {

    OrderPayRecordData toData(OrderPayRecordDO orderPayRecordDO);
}
