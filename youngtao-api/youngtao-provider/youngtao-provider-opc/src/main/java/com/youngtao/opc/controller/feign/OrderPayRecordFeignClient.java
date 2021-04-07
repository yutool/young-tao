package com.youngtao.opc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.model.arg.TradeRefundArg;
import com.youngtao.opc.api.model.dto.AlipayTradeRefundDTO;
import com.youngtao.opc.api.model.dto.OrderPayRecordDTO;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.opc.model.data.OrderPayRecordData;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import com.youngtao.opc.service.OrderPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@RestController
public class OrderPayRecordFeignClient implements OrderPayRecordFeign {
    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Override
    public RpcResult<String> addRecord(@RequestBody AddPayRecordArg arg) {
        OrderPayRecordDO record = BeanUtils.copy(arg, OrderPayRecordDO.class);
        String paymentId = orderPayRecordService.addRecord(record);
        return RpcResult.success(paymentId);
    }

    @Override
    public RpcResult<OrderPayRecordDTO> getByPaymentId(String paymentId) {
        OrderPayRecordData data = orderPayRecordService.getByPaymentId(paymentId);
        OrderPayRecordDTO dto = BeanUtils.copy(data, OrderPayRecordDTO.class);
        return RpcResult.success(dto);
    }
}
