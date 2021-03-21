package com.youngtao.omc.service.impl;

import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.utils.IdUtils;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseService<OrderDO> implements OrderService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RedisManager<String> redisManager;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    public String createOrder(CreateOrderRequest request, String userId) {
        request.setUserId(userId);
        String paymentId = IdUtils.orderId();
        request.setPaymentId(paymentId);
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMQUtils.withTag(orderTopic, MQTagConsts.CREATE_ORDER), request);
        if (sendResult == null) {
            log.warn("createOrder syncSend fail, data = {}", request);
        }
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(paymentId), OrderStatus.CREATING);
        return paymentId;
    }

    @Override
    public Integer queryStatus(String paymentId) {
        return redisManager.get(OmcRedisKey.ORDER_STATUS.format(paymentId));
    }
}
