package com.youngtao.opc.service;

import com.youngtao.opc.model.request.AlipayAppRequest;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface AlipayService {
    String appPay(AlipayAppRequest request);
}
