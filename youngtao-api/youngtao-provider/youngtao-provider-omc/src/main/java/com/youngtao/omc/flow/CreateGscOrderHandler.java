package com.youngtao.omc.flow;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcSkuFeign;
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

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class CreateGscOrderHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private GpcSkuFeign gpcSkuFeign;

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        // 活动只能购买一件商品
        CreateOrderFlowData.Order order = data.getOrderList().get(0);
        CreateOrderFlowData.OrderItem orderItem = order.getOrderItem().get(0);
        // 1 获取数据
        RpcResult<SpuDTO> spuResult = spuFeign.getBySkuId(orderItem.getSkuId());
        SpuDTO spuDTO = RpcUtils.getData(spuResult);
        RpcResult<GpcSkuDTO> skuResult = gpcSkuFeign.getBySkuId(orderItem.getMenu(), orderItem.getSkuId());
        GpcSkuDTO skuDTO = RpcUtils.getData(skuResult);

        // 订单信息
        String orderId = IdUtils.orderId();
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(orderId);
        orderDO.setMerchantId(spuDTO.getMerchantId());
        orderDO.setUserId(data.getUserId());
        orderDO.setTotalPrice(skuDTO.getPrice());
        orderDO.setActualPrice(skuDTO.getPrice());
        orderDO.setPayMoney(skuDTO.getPrice());
        orderDO.setPostage(BigDecimal.ZERO);
        orderDO.setWeight(0);
        orderDO.setRemark(data.getOrderList().get(0).getRemark());
        orderDO.setType(OrderType.SECKILL);
        orderDO.setStatus(OrderStatus.PAYMENT);
        orderDO.setPaymentId(data.getPaymentId());
        orderDO.setShippingAddressId(data.getShippingAddressId());
        // 订单项信息
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setOrderId(orderId);
        orderItemDO.setSpuId(skuDTO.getSpuId());
        orderItemDO.setSkuId(skuDTO.getSkuId());
        orderItemDO.setNum(orderItem.getCount());
        orderItemDO.setSpu(spuDTO.getSpu());
        orderItemDO.setSku(skuDTO.getSku());
        orderItemDO.setImage(skuDTO.getImage());
        orderItemDO.setOldPrice(skuDTO.getOldPrice());
        orderItemDO.setPrice(skuDTO.getPrice());
        orderItemDO.setTotalPrice(skuDTO.getPrice());

        orderMapper.insert(orderDO);
        orderItemMapper.insert(orderItemDO);
        // 保存上下文
        ctx.setPayMoney(skuDTO.getPrice());
    }
}
