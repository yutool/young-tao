package com.youngtao.omc.service.impl;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.RpcResultUtils;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.omc.common.constant.OrderConsts;
import com.youngtao.omc.common.util.IdUtils;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.support.BaseService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Service
public class OrderServiceImpl extends BaseService<OrderDO> implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(CreateOrderRequest request, String userId) {
        // 预扣库存
//        List<FreezeInventoryArg> args = Lists.newArrayList();
//        for (CreateOrderRequest.Order order : request.getData()) {
//            for (CreateOrderRequest.OrderItem item : order.getOrderItem()) {
//                FreezeInventoryArg arg = new FreezeInventoryArg();
//                arg.setSkuId(item.getSkuId());
//                arg.setNum(item.getNum());
//                args.add(arg);
//            }
//        }
//        RpcResult<Boolean> fiResult = skuFeign.batchFreezeInventory(args);
//        if (!fiResult.isSuccess()) {
//            CastException.cast(fiResult);
//        }
//        rocketMQTemplate.convertAndSend(RocketMQUtils.withTag(orderTopic, OrderTopicTag.CREATE), request);


        // get skuDTO map
        List<String> skuIds = Lists.newArrayList();
        for (CreateOrderRequest.Order order : request.getData()) {
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
        // data storage
        Long paymentId = IdUtils.paymentId();
        List<OrderItemDO> orderItemDOList = Lists.newArrayList();
        List<OrderDO> orderDOList = Lists.newArrayList();
        for (CreateOrderRequest.Order order : request.getData()) {
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
                orderItemDO.setTitle(spuDTO.getTitle());
                orderItemDO.setSku(skuDTO.getSku());
                orderItemDO.setImage(skuDTO.getImages().get(0));
                orderItemDO.setOldPrice(skuDTO.getPrice());
                orderItemDO.setPrice(skuDTO.getPrice());
                orderItemDO.setNum(item.getNum());
                BigDecimal price = BigDecimals.round(skuDTO.getPrice(), item.getNum());
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
            orderDO.setUserId(userId);
            orderDO.setTotalPrice(totalPrice);
            orderDO.setActualPrice(totalPrice);
            orderDO.setPayMoney(totalPrice);
            orderDO.setShippingAddressId(request.getShippingAddressId());
            orderDO.setPostage(postage);
            orderDO.setRemark(order.getRemark());
            orderDO.setType(OrderConsts.ORDER_TYPE);
            orderDO.setStatus(OrderConsts.PAYMENT_STATUS);
            orderDO.setPaymentId(paymentId);
            orderDOList.add(orderDO);
        }
        orderMapper.batchInsert(orderDOList);
        orderItemMapper.batchInsert(orderItemDOList);
        return paymentId;
    }

}
