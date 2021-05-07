package com.youngtao.omc.flow;

import com.google.common.collect.Lists;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.web.flow.FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class FreezeScoreHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {
    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        // 1 冻结库存
        List<UpdateStockArg> argList = Lists.newArrayList();
        for (CreateOrderFlowData.Order order : data.getOrderList()) {
            for (CreateOrderFlowData.OrderItem orderItem : order.getOrderItem()) {
                UpdateStockArg arg = new UpdateStockArg();
                arg.setSkuId(orderItem.getSkuId());
                arg.setNum(orderItem.getCount());
                argList.add(arg);
            }
        }
        RpcResult<Boolean> freezeResult = skuFeign.batchFreezeScore(argList);
        if (!freezeResult.isSuccess()) {
            // 订单创建失败
            CastException.cast("冻结库存失败");
        }
    }
}
