package com.youngtao.opc.service;

import com.youngtao.opc.model.request.WxpayAppRequest;
import com.youngtao.opc.model.response.WxpayResponse;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface WxpayService {
    WxpayResponse appPay(WxpayAppRequest request);
}
