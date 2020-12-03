package com.youngtao.omc.service.impl;

import com.youngtao.core.result.Result;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.omc.common.util.IdUtils;
import com.youngtao.omc.mapper.OrderItemMapper;
import com.youngtao.omc.mapper.OrderMapper;
import com.youngtao.omc.model.domain.OrderDO;
import com.youngtao.omc.model.domain.OrderItemDO;
import com.youngtao.omc.model.request.CreateOrderRequest;
import com.youngtao.omc.service.OrderService;
import com.youngtao.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrder(CreateOrderRequest request) {
        Date now = new Date();
        for (CreateOrderRequest.Order order : request.getData()) {
            String orderId = IdUtils.generateOrderId();
            OrderDO orderDO = request.convertToOrder(order);
            List<OrderItemDO> orderItemDOS = request.convertToOrderItem(order);
            Result<SpuDTO> result = spuFeign.getBySpuId("1533531779");
        }

        return true;
    }

}
