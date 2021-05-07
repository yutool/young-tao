package com.youngtao.omc.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.result.RpcResult;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.common.constant.MQTagConsts;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 回查订单消息
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${order-topic}",
        selectorExpression = MQTagConsts.CHECK_ORDER,
        consumerGroup = "check-order-group-tmp"
)
public class CheckOrderListener implements RocketMQListener<String> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(String paymentId) {
        log.info("回查订单, data = {}", paymentId);

        // 1 判断是否已经完成支付
        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>()
                .eq("payment_id", paymentId)
                .last("limit 1")
        );

        if (orderDO.getStatus() == OrderStatus.PAYMENT) {
            // 2 修改订单状态
            orderMapper.updateStatus(paymentId, OrderStatus.CLOSE);

            // 3 未支付回滚订单
            List<OrderDO> orderList = orderMapper.selectList(new QueryWrapper<OrderDO>()
                    .eq("payment_id", paymentId)
            );
            Set<String> orderIds = orderList.stream().map(OrderDO::getOrderId).collect(Collectors.toSet());
            List<OrderItemDO> orderItemList = orderItemMapper.selectByOrderIds(orderIds);
            List<UpdateStockArg> argList = orderItemList.stream().map(val -> {
                UpdateStockArg arg = new UpdateStockArg();
                arg.setSkuId(val.getSkuId());
                arg.setNum(val.getNum());
                return arg;
            }).collect(Collectors.toList());
            RpcResult<Boolean> unfreezeResult = skuFeign.batchUnfreezeScore(argList);
            if (!unfreezeResult.isSuccess()) {
                // 补偿
            }

            // 4 如果是秒杀订单，添加库存
        }
    }
}
