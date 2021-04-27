package com.youngtao.omc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.PayType;
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
import com.youngtao.omc.model.request.GetMerchantOrderRequest;
import com.youngtao.omc.model.request.GetUserOrderRequest;
import com.youngtao.omc.model.request.OrderRefundRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.opc.api.model.arg.TradeRefundArg;
import com.youngtao.opc.api.model.dto.AlipayTradeRefundDTO;
import com.youngtao.opc.api.service.AlipayFeign;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.BaseService;
import com.youngtao.web.util.PageUtils;
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

    @Autowired
    private AlipayFeign alipayFeign;
    @Autowired
    private OrderPayRecordFeign orderPayRecordFeign;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    public String createOrder(CreateOrderRequest request) {
        request.setUserId(AuthContext.get().getUserId());
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
    public PageInfo<OrderData> getUserOrder(GetUserOrderRequest request) {
        String userId = AuthContext.get().getUserId();
        // 获取数据
        PageHelper.startPage(request.getPage(), request.getSize());
        List<OrderDO> orderDOList = orderMapper.selectByUserIdAndStatus(userId, request.getStatus(), request.isDeleted());
        return convertToPage(orderDOList);
    }

    @Override
    public PageInfo<OrderData> getMerchantOrder(GetMerchantOrderRequest request) {
        String merchantId = AuthContext.get().getMerchantId();
        // 获取数据
        PageHelper.startPage(request.getPage(), request.getSize());
        List<OrderDO> orderDOList = orderMapper.selectByMerIdAndStatus(merchantId, request.getStatus());
        return convertToPage(orderDOList);
    }

    private PageInfo<OrderData> convertToPage(List<OrderDO> orderDOList) {
        if (CollectionUtils.isEmpty(orderDOList)) {
            return PageInfo.of(Lists.newArrayList());
        }
        PageInfo<OrderDO> pageInfo = PageInfo.of(orderDOList);
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
        return PageUtils.convert(pageInfo, orderDataList);
    }

    @Override
    public void orderRefund(OrderRefundRequest request) {
        // 1 查询订单
        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>()
                .eq("order_id", request.getOrderId())
                .eq("user_id", AuthContext.get().getUserId())
        );
        if (orderDO == null) {
            CastException.cast("订单不存在");
        }
        if (orderDO.getStatus() != OrderStatus.DELIVERY) {
            // 先支持代发货的退款
            CastException.cast("目前只支持代发货的退款");
        }
        // 2 退款
        if (orderDO.getPayType() == PayType.ALIPAY) {
            TradeRefundArg arg = new TradeRefundArg();
            arg.setOutTradeNo(orderDO.getPaymentId());
            arg.setRefundAmount(orderDO.getPayMoney());
            arg.setRefundReason(request.getRefundReason());
            arg.setOutRequestNo(orderDO.getOrderId());
            RpcResult<AlipayTradeRefundDTO> refundResult = alipayFeign.tradeRefund(arg);
            if (!refundResult.isSuccess()) {
                CastException.cast("退款失败，请稍后重试");
            }
            // 3 更新为已退款
            orderDO.setStatus(OrderStatus.REFUND);
            orderMapper.updateById(orderDO);
        } else {
            CastException.cast("未知支付方式");
        }
    }

    @Override
    public void deleteOrder(String orderId) {
        orderMapper.deleteOrder(orderId);
    }

    @Override
    public void recoverOrder(String orderId) {
        orderMapper.recoverOrder(orderId);
    }

    @Override
    public void updateStatusByMerchant(String orderId, Integer status) {
        String merchantId = AuthContext.get().getMerchantId();
        orderMapper.updateStatusByMerchant(orderId, merchantId, status);
    }

    @Override
    public void updateStatusByUser(String orderId, int status) {
        String userId = AuthContext.get().getUserId();
        orderMapper.updateStatusByUser(orderId, userId, status);
    }
}
