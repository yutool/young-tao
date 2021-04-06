package com.youngtao.omc.listener;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.api.constant.GscMQTagConsts;
import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import com.youngtao.gsc.api.model.msg.CreateOrderMessage;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.OrderType;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.common.util.IdUtils;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${order-topic}",
        selectorExpression = GscMQTagConsts.CREATE_ORDER,
        consumerGroup = "gsc-order-create-group",
        consumeMode = ConsumeMode.ORDERLY
)
public class CreateGscOrderListener implements RocketMQListener<CreateOrderMessage> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CreateOrderMessage message) {
        log.info("创建活动订单, data = {}", message);

        // 1 冻结库存
        UpdateStockArg arg = new UpdateStockArg();
        arg.setSkuId(message.getSkuId());
        arg.setNum(1);
        RpcResult<Boolean> freezeResult = skuFeign.batchFreezeScore(Lists.newArrayList(arg));
        if (!freezeResult.isSuccess()) {
            // ...
        }

        // 2 创建订单信息
        GscSkuDTO skuDTO = message.getSkuDTO();
        RpcResult<SpuDTO> spuResult = spuFeign.getBySpuId(skuDTO.getSpuId());
        SpuDTO spuDTO = spuResult.getData();
        String orderId = IdUtils.orderId();
        // 订单信息
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(orderId);
        orderDO.setMerchantId(spuDTO.getMerchantId());
        orderDO.setUserId(message.getUserId());
        orderDO.setTotalPrice(skuDTO.getPrice());
        orderDO.setActualPrice(skuDTO.getPrice());
        orderDO.setPayMoney(skuDTO.getPrice());
        orderDO.setPostage(BigDecimal.ZERO);
        orderDO.setWeight(0);
        orderDO.setRemark(message.getRemark());
        orderDO.setType(OrderType.SECKILL);
        orderDO.setStatus(OrderStatus.PAYMENT);
        orderDO.setPaymentId(message.getPaymentId());
        orderDO.setShippingAddressId(message.getShippingAddressId());
        // 订单项信息
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setOrderId(orderId);
        orderItemDO.setSpuId(skuDTO.getSpuId());
        orderItemDO.setSkuId(message.getSkuId());
        orderItemDO.setNum(1);
        orderItemDO.setTitle(spuDTO.getSpu());
        orderItemDO.setSku(skuDTO.getSku());
        orderItemDO.setImage(skuDTO.getImage());
        orderItemDO.setOldPrice(skuDTO.getOldPrice());
        orderItemDO.setPrice(skuDTO.getPrice());
        orderItemDO.setTotalPrice(skuDTO.getPrice());

        // 3 保存至数据库
        orderMapper.insert(orderDO);
        orderItemMapper.insert(orderItemDO);

        // 4 添加支付记录
        AddPayRecordArg addArg = new AddPayRecordArg();
        addArg.setPaymentId(message.getPaymentId());
        addArg.setUserId(message.getUserId());
        addArg.setMoney(skuDTO.getPrice());
        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);

        // 5 修改订单状态为已创建
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.PAYMENT);

        // 6 发送延迟MQ回查订单
        try {
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            Message msg = new Message(orderTopic, MQTagConsts.CHECK_ORDER, message.getPaymentId().getBytes());
            msg.setDelayTimeLevel(16);
            producer.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
