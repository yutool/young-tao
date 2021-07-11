package com.youngtao.opc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.opc.api.model.arg.TradeRefundArg;
import com.youngtao.opc.api.model.dto.AlipayTradeRefundDTO;
import com.youngtao.opc.api.service.AlipayFeign;
import com.youngtao.opc.model.req.TradeRefundReq;
import com.youngtao.opc.model.response.TradeRefundRes;
import com.youngtao.opc.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@RestController
public class AlipayFeignClient implements AlipayFeign {
    @Autowired
    private AlipayService alipayService;

    @Override
    public RpcResult<AlipayTradeRefundDTO> tradeRefund(@RequestBody TradeRefundArg arg) {
        TradeRefundReq request = BeanUtils.copy(arg, TradeRefundReq.class);
        TradeRefundRes response = alipayService.tradeRefund(request);
        if (response == null) {
            return RpcResult.error();
        }
        AlipayTradeRefundDTO dto = BeanUtils.copy(response, AlipayTradeRefundDTO.class);
        return RpcResult.success(dto);
    }
}
