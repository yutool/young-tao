package com.youngtao.opc.service;

import com.youngtao.opc.model.request.AlipayAppCheckRequest;
import com.youngtao.opc.model.request.AlipayRequest;

import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface AlipayService {
    String appPay(AlipayRequest request);

    String payNotify(Map<String, String> resultMap);

    boolean check(AlipayAppCheckRequest request);

    String webPay(AlipayRequest request);
}
