package com.youngtao.gsc.service.impl;

import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.ResponseCode;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.api.constant.GscMQTagConsts;
import com.youngtao.gsc.api.model.msg.CreateOrderMsg;
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
    public String createOrder(CreateOrderRequest request, String userId) {
        String menu = DateUtils.currentMenu();
        String skuId = request.getSkuId();
        String paymentId = IdUtils.orderId();
        // 扣减库存
        long count = redisManager.decrement(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
        if (count < 0) {
            redisManager.increment(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
        }
        // 获取sku信息
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        // 发送消息
        CreateOrderMsg msg = new CreateOrderMsg();
        msg.setUserId(userId);
        msg.setPaymentId(paymentId);
        msg.setRemark(request.getRemark());
        msg.setSkuId(skuId);
        msg.setSpuId(skuData.getSpuId());
        msg.setSku(skuData.getSku());
        msg.setImage(skuData.getImage());
        msg.setOldPrice(skuData.getOldPrice());
        msg.setPrice(skuData.getPrice());
        msg.setShippingAddressId(request.getShippingAddressId());
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(RocketMQUtils.withTag(orderTopic, GscMQTagConsts.CREATE_ORDER), msg, skuId);
        if (sendResult == null) {
            log.warn("prepareOrder syncSendOrderly fail, data = {}", request);
            CastException.cast(ResponseCode.SERVICE_ERROR);
        }
        // 设置状态
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(paymentId), OrderStatus.CREATING);
        return paymentId;
    }
}
