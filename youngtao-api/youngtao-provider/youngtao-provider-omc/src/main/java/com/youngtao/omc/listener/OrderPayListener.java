package com.youngtao.omc.listener;

import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.common.constant.OrderConsts;
import com.youngtao.omc.mapper.OrderMapper;
import lombok.Data;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/01/31
 */
@Component
@RocketMQMessageListener(
        topic = "${order-pay-topic}",
        selectorExpression = MQTagConsts.PAY_SUCCESS,
        consumerGroup = "order-pay-group"
)
public class OrderPayListener implements RocketMQListener<OrderPayListener.Message> {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void onMessage(Message msg) {
        orderMapper.updateStatus(msg.getPaymentId(), OrderConsts.DELIVERY_STATUS);
    }

    @Data
    static class Message {
        private String paymentId;
    }
}
