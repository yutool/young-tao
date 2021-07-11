package com.youngtao.opc.service;

import com.youngtao.opc.model.req.WxpayAppReq;
import com.youngtao.opc.model.response.WxpayRes;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public interface WxpayService {
    WxpayRes appPay(WxpayAppReq request);
}
