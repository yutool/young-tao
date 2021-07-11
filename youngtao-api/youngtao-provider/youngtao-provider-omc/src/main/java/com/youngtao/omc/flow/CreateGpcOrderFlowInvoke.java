package com.youngtao.omc.flow;

import com.google.common.collect.Lists;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.flow.FlowHandler;
import com.youngtao.web.flow.FlowInvoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class CreateGpcOrderFlowInvoke implements FlowInvoke<CreateOrderFlowData> {
    @Autowired
    private FreezeScoreHandler freezeScoreHandler;
    @Autowired
    private CreateGpcOrderHandler createGpcOrderHandler;
    @Autowired
    private PayRecordHandler payRecordHandler;
    @Autowired
    private SendMQHandler sendMQHandler;

    private List<FlowHandler<CreateOrderFlowData, CreateOrderFlowContext>> handlerChain;

    @Autowired
    private RedisManager<String> redisManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoke(CreateOrderFlowData data) {
        CreateOrderFlowContext context = new CreateOrderFlowContext();
        try {
            for (FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> handler : handlerChain) {
                handler.handle(data, context);
            }
            redisManager.set(OmcRedisKey.ORDER_STATUS.format(data.getPaymentId()), OrderStatus.PAYMENT);
        } catch (Exception ex) {
            redisManager.set(OmcRedisKey.ORDER_STATUS.format(data.getPaymentId()), OrderStatus.FAILED);
            throw ex;
        }
    }

    @Override
    public void afterPropertiesSet() {
        handlerChain = Lists.newArrayList();
        handlerChain.add(freezeScoreHandler);
        handlerChain.add(createGpcOrderHandler);
        handlerChain.add(payRecordHandler);
        handlerChain.add(sendMQHandler);
    }
}
