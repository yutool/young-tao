package com.youngtao.opc.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.google.common.collect.Maps;
import com.youngtao.opc.common.constant.WxpayEnum;
import com.youngtao.opc.config.WxpayConfig;
import com.youngtao.opc.model.request.WxpayAppRequest;
import com.youngtao.opc.model.response.WxpayResponse;
import com.youngtao.opc.service.WxpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Service
public class WxpayServiceImpl implements WxpayService {
    @Autowired
    private WXPay wxpay;
    @Autowired
    private WxpayConfig config;

    @Override
    public WxpayResponse appPay(WxpayAppRequest request) {
        Map<String, String> data = Maps.newHashMap();
        data.put("out_trade_no", request.getPaymentId());
        data.put("body", request.getBody());
        // data.put("total_fee", request.getMoney());
        data.put("total_fee", "1");
        data.put("notify_url", config.getPayNotifyUrl());
        data.put("trade_type", "APP");
        try {
            Map<String, String> resultMap = wxpay.unifiedOrder(data);
            if (WxpayEnum.SUCCESS.getValue().equals(resultMap.get("xxx"))) {
            }
            if (WxpayEnum.SUCCESS.getValue().equals(resultMap.get("xxx"))) {
            }
            WxpayResponse response = new WxpayResponse();
            response.setAppId(resultMap.get("appid"));
            response.setPartnerId(resultMap.get("mch_id"));
            response.setPrepayId(resultMap.get("prepay_id"));
            response.setNoncestr(resultMap.get("nonce_str"));
            response.setSign(resultMap.get("sign"));
            response.setPackage("Sign=WXPay");
            response.setTimestamp(System.currentTimeMillis()+"");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
