package com.youngtao.opc.service.impl;

import com.youngtao.opc.api.model.constant.PayRecordStatus;
import com.youngtao.opc.mapper.OrderPayRecordMapper;
import com.youngtao.opc.model.convert.OrderPayRecordConvert;
import com.youngtao.opc.model.data.OrderPayRecordData;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import com.youngtao.opc.service.OrderPayRecordService;
import com.youngtao.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
@Service
public class OrderPayRecordServiceImpl extends BaseService<OrderPayRecordDO> implements OrderPayRecordService {
    @Resource
    private OrderPayRecordMapper orderPayRecordMapper;
    @Autowired
    private OrderPayRecordConvert orderPayRecordConvert;

    @Override
    public OrderPayRecordData getByPaymentId(String id) {
        OrderPayRecordDO record = orderPayRecordMapper.selectByPaymentId(id);
        return orderPayRecordConvert.toData(record);
    }

    @Override
    public String addRecord(OrderPayRecordDO record) {
        record.setStatus(PayRecordStatus.UNPAID);
        orderPayRecordMapper.insert(record);
        return record.getPaymentId();
    }
}
