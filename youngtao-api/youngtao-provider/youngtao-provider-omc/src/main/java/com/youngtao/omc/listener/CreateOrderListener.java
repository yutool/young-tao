package com.youngtao.omc.listener;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.OrderType;
import com.youngtao.omc.api.utils.IdUtils;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.mapper.CartMapper;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 创建订单消息
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${order-topic}",
        selectorExpression = MQTagConsts.CREATE_ORDER,
        consumerGroup = "order-create-group"
)
public class CreateOrderListener implements RocketMQListener<CreateOrderRequest> {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private CartMapper cartMapper;
    @Autowired
    private RedisManager<String> redisManager;

    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CreateOrderRequest message) {
        log.info("创建订单中, data = {}", message);

        // 1 冻结库存
        List<UpdateStockArg> argList = Lists.newArrayList();
        for (CreateOrderRequest.Order order : message.getOrderList()) {
            for (CreateOrderRequest.OrderItem orderItem : order.getOrderItem()) {
                UpdateStockArg arg = new UpdateStockArg();
                arg.setSkuId(orderItem.getSkuId());
                arg.setNum(orderItem.getCount());
                argList.add(arg);
            }
        }
        RpcResult<Boolean> freezeResult = skuFeign.batchFreezeScore(argList);
        if (!freezeResult.isSuccess()) {
            // 订单创建失败
            redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.FAILED);
            return;
        }

        // 2 创建订单信息
        List<String> skuIds = Lists.newArrayList();
        for (CreateOrderRequest.Order order : message.getOrderList()) {
            for (CreateOrderRequest.OrderItem item : order.getOrderItem()) {
                skuIds.add(item.getSkuId());
            }
        }
        RpcResult<List<SkuDTO>> skuListResult = skuFeign.listBySkuIds(skuIds);
        RpcResultUtils.checkNotNull(skuListResult);
        Map<String, SkuDTO> skuDTOMap = skuListResult.getData().stream().collect(Collectors.toMap(SkuDTO::getSkuId, val -> val));
        // get SpuDTO map
        Set<String> spuIds = skuListResult.getData().stream().map(SkuDTO::getSpuId).collect(Collectors.toSet());
        RpcResult<List<SpuDTO>> spuListResult = spuFeign.listBySpuIds(spuIds);
        RpcResultUtils.checkNotNull(spuListResult);
        Map<String, SpuDTO> spuDTOMap = spuListResult.getData().stream().collect(Collectors.toMap(SpuDTO::getSpuId, val -> val));
        // 将信息转为订单信息
        List<OrderItemDO> orderItemDOList = Lists.newArrayList();
        List<OrderDO> orderDOList = Lists.newArrayList();
        BigDecimal payMoney = BigDecimal.ZERO;
        for (CreateOrderRequest.Order order : message.getOrderList()) {
            String orderId = IdUtils.orderId();
            // sku
            BigDecimal totalPrice = BigDecimal.ZERO;
            BigDecimal postage = BigDecimals.MAX_INT_VALUE;
            String merchantId = null;
            for (CreateOrderRequest.OrderItem item : order.getOrderItem()) {
                SkuDTO skuDTO = skuDTOMap.get(item.getSkuId());
                SpuDTO spuDTO = spuDTOMap.get(skuDTO.getSpuId());
                OrderItemDO orderItemDO = new OrderItemDO();
                orderItemDO.setOrderId(orderId);
                orderItemDO.setSpuId(skuDTO.getSpuId());
                orderItemDO.setSkuId(item.getSkuId());
                orderItemDO.setTitle(spuDTO.getSpu());
                orderItemDO.setSku(skuDTO.getSku());
                orderItemDO.setImage(skuDTO.getImages().get(0));
                orderItemDO.setOldPrice(skuDTO.getPrice());
                orderItemDO.setPrice(skuDTO.getPrice());
                orderItemDO.setNum(item.getCount());
                BigDecimal price = BigDecimals.multiRound(skuDTO.getPrice(), item.getCount());
                orderItemDO.setTotalPrice(price);
                orderItemDOList.add(orderItemDO);

                totalPrice = totalPrice.add(price);
                postage = postage.min(spuDTO.getPostage());
                merchantId = spuDTO.getMerchantId();
            }
            // spu
            OrderDO orderDO = new OrderDO();
            orderDO.setOrderId(orderId);
            orderDO.setMerchantId(merchantId);
            orderDO.setUserId(message.getUserId());
            orderDO.setTotalPrice(totalPrice);
            orderDO.setActualPrice(totalPrice);
            orderDO.setPayMoney(totalPrice);
            orderDO.setShippingAddressId(message.getShippingAddressId());
            orderDO.setPostage(postage);
            orderDO.setRemark(order.getRemark());
            orderDO.setType(OrderType.NORMAL);
            orderDO.setStatus(OrderStatus.PAYMENT);
            orderDOList.add(orderDO);
            payMoney = payMoney.add(totalPrice);
        }

        // 3 保存至数据库
        for (OrderDO orderDO : orderDOList) {
            orderDO.setPaymentId(message.getPaymentId());
        }
        orderMapper.batchInsert(orderDOList);
        orderItemMapper.batchInsert(orderItemDOList);

        // 4 删除购物车
        if (message.getIsCart()) {
            cartMapper.batchDelete(message.getUserId(), skuIds);
        }

        // 5 添加支付记录
        AddPayRecordArg addArg = new AddPayRecordArg();
        addArg.setPaymentId(message.getPaymentId());
        addArg.setUserId(message.getUserId());
        addArg.setMoney(payMoney);
        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);
        RpcResultUtils.checkNotNull(paymentResult);

        // 6 修改订单状态为已创建
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.PAYMENT);

        // 7 发送延迟MQ回查订单
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
