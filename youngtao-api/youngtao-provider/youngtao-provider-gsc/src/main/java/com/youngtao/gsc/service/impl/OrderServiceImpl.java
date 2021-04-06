package com.youngtao.gsc.service.impl;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.ResponseCode;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.api.constant.GscMQTagConsts;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.model.msg.CreateOrderMessage;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.common.util.IdUtils;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.request.CreateOrderRequest;
import com.youngtao.gsc.service.OrderService;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private DCacheManager<String> dCacheManager;
    @Autowired
    private SpuFeign spuFeign;

    @Value("${topic.order}")
    private String orderTopic;

    @Override
    public String createOrder(CreateOrderRequest request) {
        String menu = DateUtils.currentMenu();
        String skuId = request.getSkuId();
        String paymentId = IdUtils.orderId();
        String userId = AuthContext.get().getUserId();
        // 1 redis扣减库存
        long count = redisManager.decrement(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
        if (count < 0) {
            redisManager.increment(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
            CastException.cast("库存不足");
        }

        // 2 设置订单状态
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(paymentId), OrderStatus.CREATING);

        // 3 封装并发送消息
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        CreateOrderMessage msg = new CreateOrderMessage();
        msg.setPaymentId(paymentId);
        msg.setUserId(userId);
        msg.setRemark(request.getRemark());
        msg.setSkuId(skuId);
        msg.setShippingAddressId(request.getShippingAddressId());
        msg.setSkuDTO(BeanUtils.copy(skuData, GscSkuDTO.class));
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(RocketMQUtils.withTag(orderTopic, GscMQTagConsts.CREATE_ORDER), msg, skuId);
        if (sendResult == null) {
            log.warn("prepareOrder syncSendOrderly fail, data = {}", request);
            redisManager.increment(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
            CastException.cast(ResponseCode.SERVICE_ERROR);
        }
        return paymentId;
    }
}
