package com.youngtao.opc.service;

import com.youngtao.opc.model.data.OrderPayRecordData;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import com.youngtao.web.support.IService;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/01/17
 */
public interface OrderPayRecordService extends IService<OrderPayRecordDO> {
    /**
     * 根据支付id查找
     */
    OrderPayRecordData getByPaymentId(String id);

    /**
     * 添加支付记录
     */
    String addRecord(BigDecimal money);
}
