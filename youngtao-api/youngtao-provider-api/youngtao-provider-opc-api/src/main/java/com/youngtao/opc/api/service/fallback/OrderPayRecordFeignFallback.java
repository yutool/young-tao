package com.youngtao.opc.api.service.fallback;

import com.youngtao.core.result.RpcResult;
import com.youngtao.opc.api.model.arg.AddPayRecordArg;
import com.youngtao.opc.api.service.OrderPayRecordFeign;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
public class OrderPayRecordFeignFallback implements OrderPayRecordFeign {
    @Override
    public RpcResult<String> addRecord(@RequestBody AddPayRecordArg arg) {
        return null;
    }
}
