package com.youngtao.opc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.service.OrderPayRecordFeign;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
public class OrderPayRecordFeignFallback implements OrderPayRecordFeign {
    @Override
    public RpcResult<String> addRecord(BigDecimal money) {
        return null;
    }
}
