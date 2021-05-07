package com.youngtao.omc.flow;

import com.youngtao.core.exception.CastException;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.web.flow.FlowHandler;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class SendMQHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        // 7 发送延迟MQ回查订单
        try {
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            Message msg = new Message(orderTopic, MQTagConsts.CHECK_ORDER, data.getPaymentId().getBytes());
            msg.setDelayTimeLevel(16);
            producer.send(msg);
        } catch (Exception e) {
            CastException.cast("发送回查MQ失败");
        }
    }
}
