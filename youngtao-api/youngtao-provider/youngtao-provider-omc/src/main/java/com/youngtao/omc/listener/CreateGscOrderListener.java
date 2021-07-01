package com.youngtao.omc.listener;

import com.youngtao.core.util.BeanUtils;
import com.youngtao.gpc.api.constant.GpcMQTagConsts;
import com.youngtao.omc.api.model.msg.CreateOrderMessage;
import com.youngtao.omc.flow.CreateGscOrderFlowInvoke;
import com.youngtao.omc.flow.CreateOrderFlowData;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${order-topic}",
        selectorExpression = GpcMQTagConsts.CREATE_ORDER,
        consumerGroup = "gsc-order-create-group-tmp",
        consumeMode = ConsumeMode.ORDERLY
)
public class CreateGscOrderListener implements RocketMQListener<CreateOrderMessage> {
    @Autowired
    private CreateGscOrderFlowInvoke invoke;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CreateOrderMessage message) {
        log.info("创建活动订单, data = {}", message);
        CreateOrderFlowData data = BeanUtils.copy(message, CreateOrderFlowData.class);
        invoke.invoke(data);


//        // 1 冻结库存
//        UpdateStockArg arg = new UpdateStockArg();
//        arg.setSkuId(message.getSkuId());
//        arg.setNum(1);
//        RpcResult<Boolean> freezeResult = skuFeign.batchFreezeScore(Lists.newArrayList(arg));
//        if (!freezeResult.isSuccess()) {
//            // ...
//        }
//
//        // 2 创建订单信息
//        GscSkuDTO skuDTO = message.getSkuDTO();
//        RpcResult<SpuDTO> spuResult = spuFeign.getBySpuId(skuDTO.getSpuId());
//        SpuDTO spuDTO = spuResult.getData();
//        String orderId = IdUtils.orderId();
//        // 订单信息
//        OrderDO orderDO = new OrderDO();
//        orderDO.setOrderId(orderId);
//        orderDO.setMerchantId(spuDTO.getMerchantId());
//        orderDO.setUserId(message.getUserId());
//        orderDO.setTotalPrice(skuDTO.getPrice());
//        orderDO.setActualPrice(skuDTO.getPrice());
//        orderDO.setPayMoney(skuDTO.getPrice());
//        orderDO.setPostage(BigDecimal.ZERO);
//        orderDO.setWeight(0);
//        orderDO.setRemark(message.getRemark());
//        orderDO.setType(OrderType.SECKILL);
//        orderDO.setStatus(OrderStatus.PAYMENT);
//        orderDO.setPaymentId(message.getPaymentId());
//        orderDO.setShippingAddressId(message.getShippingAddressId());
//        // 订单项信息
//        OrderItemDO orderItemDO = new OrderItemDO();
//        orderItemDO.setOrderId(orderId);
//        orderItemDO.setSpuId(skuDTO.getSpuId());
//        orderItemDO.setSkuId(message.getSkuId());
//        orderItemDO.setNum(1);
//        orderItemDO.setSpu(spuDTO.getSpu());
//        orderItemDO.setSku(skuDTO.getSku());
//        orderItemDO.setImage(skuDTO.getImage());
//        orderItemDO.setOldPrice(skuDTO.getOldPrice());
//        orderItemDO.setPrice(skuDTO.getPrice());
//        orderItemDO.setTotalPrice(skuDTO.getPrice());
//
//        // 3 保存至数据库
//        orderMapper.insert(orderDO);
//        orderItemMapper.insert(orderItemDO);
//
//        // 4 删除购物车
//        if (message.getIsCart()) {
//            cartMapper.deleteBySkuId(message.getUserId(), skuDTO.getSkuId());
//        }
//
//        // 4 添加支付记录
//        AddPayRecordArg addArg = new AddPayRecordArg();
//        addArg.setPaymentId(message.getPaymentId());
//        addArg.setUserId(message.getUserId());
//        addArg.setMoney(skuDTO.getPrice());
//        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);
//
//        // 5 修改订单状态为已创建
//        redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.PAYMENT);
//
//        // 6 发送延迟MQ回查订单
//        try {
//            DefaultMQProducer producer = rocketMQTemplate.getProducer();
//            Message msg = new Message(orderTopic, MQTagConsts.CHECK_ORDER, message.getPaymentId().getBytes());
//            msg.setDelayTimeLevel(16);
//            producer.send(msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
