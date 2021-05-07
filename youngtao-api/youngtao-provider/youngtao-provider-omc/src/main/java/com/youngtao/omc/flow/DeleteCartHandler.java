package com.youngtao.omc.flow;

import com.youngtao.web.flow.FlowHandler;
import com.youngtao.omc.mapper.CartMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Component
public class DeleteCartHandler implements FlowHandler<CreateOrderFlowData, CreateOrderFlowContext> {
    @Resource
    private CartMapper cartMapper;

    @Override
    public void handle(CreateOrderFlowData data, CreateOrderFlowContext ctx) {
        if (data.getIsCart()) {
            cartMapper.batchDelete(data.getUserId(), ctx.getSkuIds());
        }
    }
}
