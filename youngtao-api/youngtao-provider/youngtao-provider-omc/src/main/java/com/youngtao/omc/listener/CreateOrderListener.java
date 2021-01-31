package com.youngtao.omc.listener;

import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.model.request.CreateOrderRequest;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
@RocketMQMessageListener(
        topic = "${order.topic}",
        selectorExpression = MQTagConsts.CREATE_ORDER,
        consumerGroup = "order-create-group"
)
public class CreateOrderListener implements RocketMQListener<CreateOrderRequest> {
    @Override
    public void onMessage(CreateOrderRequest message) {

    }
}
