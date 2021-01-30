package com.youngtao.opc.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.youngtao.opc.model.request.AlipayAppRequest;
import com.youngtao.opc.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayClient alipay;

    @Override
    public String appPay(AlipayAppRequest request) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(request.getPaymentId());
        model.setSubject(request.getSubject());
        model.setBody(request.getBody());
        // model.setTotalAmount(BigDecimals.round(request.getMoney()));
        model.setTotalAmount("0.01");
        model.setTimeoutExpress("30m");
        model.setProductCode("QUICK_MSECURITY_PAY");
        AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
        payRequest.setBizModel(model);

        try {
            AlipayTradeAppPayResponse response = alipay.sdkExecute(payRequest);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
