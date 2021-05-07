package com.youngtao.omc.flow;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.flow.FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class PayRecordHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {

    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        // 5 添加支付记录
        AddPayRecordArg addArg = new AddPayRecordArg();
        addArg.setPaymentId(data.getPaymentId());
        addArg.setUserId(data.getUserId());
        addArg.setMoney(ctx.getPayMoney());
        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);
        RpcResultUtils.checkNotNull(paymentResult);
    }
}
