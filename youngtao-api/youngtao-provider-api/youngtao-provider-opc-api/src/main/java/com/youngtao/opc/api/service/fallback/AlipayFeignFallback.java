package com.youngtao.opc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.model.arg.TradeRefundArg;
import com.youngtao.opc.api.model.dto.AlipayTradeRefundDTO;
import com.youngtao.opc.api.service.AlipayFeign;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
public class AlipayFeignFallback implements AlipayFeign {
    @Override
    public RpcResult<AlipayTradeRefundDTO> tradeRefund(TradeRefundArg arg) {
        return null;
    }
}
