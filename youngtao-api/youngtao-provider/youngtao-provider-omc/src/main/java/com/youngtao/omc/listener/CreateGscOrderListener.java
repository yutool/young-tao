package com.youngtao.omc.listener;

import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.api.constant.GscMQTagConsts;
import com.youngtao.gsc.api.model.msg.CreateOrderMsg;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.OrderType;
import com.youngtao.omc.common.util.IdUtils;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.cache.RedisManager;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@RocketMQMessageListener(
        topic = "${order-topic}",
        selectorExpression = GscMQTagConsts.CREATE_ORDER,
        consumerGroup = "gsc-order-create-group",
        consumeMode = ConsumeMode.ORDERLY
)
@Component
public class CreateGscOrderListener implements RocketMQListener<CreateOrderMsg> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CreateOrderMsg message) {
        // 冻结库存

        // 生成订单
        RpcResult<SpuDTO> spuResult = spuFeign.getBySpuId(message.getSpuId());
        SpuDTO spuDTO = spuResult.getData();
        String orderId = IdUtils.orderId();
        // 订单信息
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(orderId);
        orderDO.setMerchantId(spuDTO.getMerchantId());
        orderDO.setUserId(message.getUserId());
        orderDO.setTotalPrice(message.getPrice());
        orderDO.setActualPrice(message.getPrice());
        orderDO.setPayMoney(message.getPrice());
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
        orderItemDO.setSpuId(message.getSpuId());
        orderItemDO.setSkuId(message.getSkuId());
        orderItemDO.setNum(1);
        orderItemDO.setTitle(spuDTO.getSpu());
        orderItemDO.setSku(message.getSku());
        orderItemDO.setImage(message.getImage());
        orderItemDO.setOldPrice(message.getOldPrice());
        orderItemDO.setPrice(message.getPrice());
        orderItemDO.setTotalPrice(message.getPrice());
        // 存储
        orderMapper.insert(orderDO);
        orderItemMapper.insert(orderItemDO);
        AddPayRecordArg addArg = new AddPayRecordArg();
        addArg.setPaymentId(message.getPaymentId());
        addArg.setMoney(message.getPrice());
        RpcResult<String> paymentResult = orderPayRecordFeign.addRecord(addArg);
        // 更新状态
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(message.getPaymentId()), OrderStatus.PAYMENT);
    }
}
