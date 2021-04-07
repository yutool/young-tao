package com.youngtao.omc.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 订单支付消息
 * @author ankoye@qq.com
 * @date 2021/01/31
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${order-pay-topic}",
        selectorExpression = MQTagConsts.PAY_SUCCESS,
        consumerGroup = "order-pay-group"
)
public class OrderPayListener implements RocketMQListener<OrderPayListener.Message> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public void onMessage(Message message) {
        log.info("订单支付信息, data = {}", message);

        // 1 修改订单信息
        orderMapper.paySuccess(message.getPaymentId(), message.getPayType(), OrderStatus.DELIVERY);

        // 2 删除库存，增加销量
        List<OrderDO> orderList = orderMapper.selectList(new QueryWrapper<OrderDO>()
                .eq("payment_id", message.getPaymentId())
        );
        Set<String> orderIds = orderList.stream().map(OrderDO::getOrderId).collect(Collectors.toSet());
        List<OrderItemDO> orderItemList = orderItemMapper.selectByOrderIds(orderIds);
        List<UpdateStockArg> argList = orderItemList.stream().map(val -> {
            UpdateStockArg arg = new UpdateStockArg();
            arg.setSkuId(val.getSkuId());
            arg.setNum(val.getNum());
            return arg;
        }).collect(Collectors.toList());
        RpcResult<Boolean> decreaseResult = productFeign.paySuccess(argList);
        if (!decreaseResult.isSuccess()) {
            // 补偿
        }
    }

    @Data
    static class Message {
        private String paymentId;
        private Integer payType;
    }
}
