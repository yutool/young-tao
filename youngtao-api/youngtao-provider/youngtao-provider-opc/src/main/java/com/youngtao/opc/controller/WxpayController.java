package com.youngtao.opc.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.youngtao.opc.config.WxpayConfig;
import com.youngtao.opc.model.request.WxpayAppRequest;
import com.youngtao.opc.model.response.WxpayResponse;
import com.youngtao.opc.service.WxpayService;
import com.youngtao.web.support.NoWrapper;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@ResponseWrapper
@RestController
@RequestMapping("/wxpay")
public class WxpayController {
    @Autowired
    private WxpayService wxpayService;
    @Autowired
    private WxpayConfig config;

    @GetMapping("/app")
    public WxpayResponse appPay(@RequestBody WxpayAppRequest request) {
        return wxpayService.appPay(request);
    }

    /**
     * 支付回调
     */
    @NoWrapper
    @PostMapping("/payNotify")
    public String payNotify(HttpServletRequest request) {
        try(ServletInputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            String resultXml = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
            if (WXPayUtil.isSignatureValid(resultMap, config.getKey())) {
                // 发送MQ，处理订单状态
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
