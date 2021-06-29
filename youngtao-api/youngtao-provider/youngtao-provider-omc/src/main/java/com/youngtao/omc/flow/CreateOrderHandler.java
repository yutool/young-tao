package com.youngtao.omc.flow;

import com.google.common.collect.Lists;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gsc.api.service.GscSkuFeign;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.constant.OrderType;
import com.youngtao.omc.api.utils.IdUtils;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.web.flow.FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class CreateOrderHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private GscSkuFeign gscSkuFeign;

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        List<String> skuIds = Lists.newArrayList();
        for (CreateOrderFlowData.Order order : data.getOrderList()) {
            for (CreateOrderFlowData.OrderItem item : order.getOrderItem()) {
                skuIds.add(item.getSkuId());
            }
        }
        RpcResult<List<SkuDTO>> skuListResult = skuFeign.listBySkuIds(skuIds);
        RpcUtils.checkNotNull(skuListResult);
        Map<String, SkuDTO> skuDTOMap = skuListResult.getData().stream().collect(Collectors.toMap(SkuDTO::getSkuId, val -> val));
        // get SpuDTO map
        Set<String> spuIds = skuListResult.getData().stream().map(SkuDTO::getSpuId).collect(Collectors.toSet());
        RpcResult<List<SpuDTO>> spuListResult = spuFeign.listBySpuIds(spuIds);
        RpcUtils.checkNotNull(spuListResult);
        Map<String, SpuDTO> spuDTOMap = spuListResult.getData().stream().collect(Collectors.toMap(SpuDTO::getSpuId, val -> val));
        // 将信息转为订单信息
        List<OrderItemDO> orderItemDOList = Lists.newArrayList();
        List<OrderDO> orderDOList = Lists.newArrayList();
        BigDecimal payMoney = BigDecimal.ZERO;
        for (CreateOrderFlowData.Order order : data.getOrderList()) {
            String orderId = IdUtils.orderId();
            // sku
            BigDecimal totalPrice = BigDecimal.ZERO;
            BigDecimal postage = BigDecimals.MAX_INT_VALUE;
            String merchantId = null;
            for (CreateOrderFlowData.OrderItem item : order.getOrderItem()) {
                SkuDTO skuDTO = skuDTOMap.get(item.getSkuId());
                SpuDTO spuDTO = spuDTOMap.get(skuDTO.getSpuId());
                OrderItemDO orderItemDO = new OrderItemDO();
                orderItemDO.setOrderId(orderId);
                orderItemDO.setSpuId(skuDTO.getSpuId());
                orderItemDO.setSkuId(item.getSkuId());
                orderItemDO.setSpu(spuDTO.getSpu());
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
            orderDO.setUserId(data.getUserId());
            orderDO.setTotalPrice(totalPrice);
            orderDO.setActualPrice(totalPrice);
            orderDO.setPayMoney(totalPrice);
            orderDO.setShippingAddressId(data.getShippingAddressId());
            orderDO.setPostage(postage);
            orderDO.setRemark(order.getRemark());
            orderDO.setType(OrderType.NORMAL);
            orderDO.setStatus(OrderStatus.PAYMENT);
            orderDOList.add(orderDO);
            payMoney = payMoney.add(totalPrice);
        }

        // 3 保存至数据库
        for (OrderDO orderDO : orderDOList) {
            orderDO.setPaymentId(data.getPaymentId());
        }
        orderMapper.batchInsert(orderDOList);
        orderItemMapper.batchInsert(orderItemDOList);

        // 保存
        ctx.setSkuIds(skuIds);
        ctx.setPayMoney(payMoney);
    }
}
