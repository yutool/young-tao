package com.youngtao.omc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.utils.IdUtils;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.convert.OrderConvert;
import com.youngtao.omc.model.convert.OrderItemConvert;
import com.youngtao.omc.model.data.OrderData;
import com.youngtao.omc.model.data.OrderItemData;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.model.request.GetUserOrderRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderConvert orderConvert;
    @Autowired
    private OrderItemConvert orderItemConvert;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    public String createOrder(CreateOrderRequest request, String userId) {
        request.setUserId(userId);
        String paymentId = IdUtils.orderId();
        request.setPaymentId(paymentId);
        // 1 设置当前订单创建中
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(paymentId), OrderStatus.CREATING);

        // 2 发送MQ，进行扣减库存
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMQUtils.withTag(orderTopic, MQTagConsts.CREATE_ORDER), request);
        if (sendResult == null) {
            log.warn("createOrder syncSend fail, data = {}", request);
        }
        return paymentId;
    }

    @Override
    public Integer queryStatus(String paymentId) {
        return redisManager.get(OmcRedisKey.ORDER_STATUS.format(paymentId));
    }

    @Override
    public PageInfo<OrderData> getUserOrder(GetUserOrderRequest request, String userId) {
        // 获取数据
        PageHelper.startPage(request.getPage(), request.getSize());
        List<OrderDO> orderDOList = orderMapper.selectByUserIdAndStatus(userId, request.getStatus());
        if (CollectionUtils.isEmpty(orderDOList)) {
            return PageInfo.of(Lists.newArrayList());
        }
        Set<String> orderIds = orderDOList.stream().map(OrderDO::getOrderId).collect(Collectors.toSet());
        List<OrderItemDO> orderItemDOList = orderItemMapper.selectByOrderIds(orderIds);
        Map<String, List<OrderItemDO>> orderItemMap = Maps.newHashMap();
        for (OrderItemDO orderItemDO : orderItemDOList) {
            List<OrderItemDO> list = orderItemMap.getOrDefault(orderItemDO.getOrderId(), Lists.newArrayList());
            list.add(orderItemDO);
            orderItemMap.put(orderItemDO.getOrderId(), list);
        }
        // 转换数据
        List<OrderData> orderDataList = orderConvert.toOrderData(orderDOList);
        for (OrderData orderData : orderDataList) {
            List<OrderItemDO> list = orderItemMap.get(orderData.getOrderId());
            List<OrderItemData> orderItemData = orderItemConvert.toOrderItemData(list);
            orderData.setOrderItem(orderItemData);
        }
        return PageInfo.of(orderDataList);
    }
}
