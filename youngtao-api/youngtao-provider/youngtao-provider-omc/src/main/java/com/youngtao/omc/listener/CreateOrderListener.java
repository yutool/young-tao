package com.youngtao.omc.listener;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.OrderType;
import com.youngtao.omc.api.utils.IdUtils;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
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
    @Autowired
    private RedisManager<String> redisManager;

    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CreateOrderRequest message) {
        // 1 冻结库存

        // get skuDTO map
        List<String> skuIds = Lists.newArrayList();
        for (CreateOrderRequest.Order order : message.getData()) {
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
        for (CreateOrderRequest.Order order : message.getData()) {
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
                orderItemDO.setNum(item.getNum());
                BigDecimal price = BigDecimals.multiRound(skuDTO.getPrice(), item.getNum());
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
        // 添加支付记录
        AddPayRecordArg addArg = new AddPayRecordArg();
        addArg.setPaymentId(message.getPaymentId());
        addArg.setMoney(payMoney);
        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);
        RpcResultUtils.checkNotNull(paymentResult);
        for (OrderDO orderDO : orderDOList) {
            orderDO.setPaymentId(message.getPaymentId());
        }
        orderMapper.batchInsert(orderDOList);
        orderItemMapper.batchInsert(orderItemDOList);
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.PAYMENT);
    }
}
