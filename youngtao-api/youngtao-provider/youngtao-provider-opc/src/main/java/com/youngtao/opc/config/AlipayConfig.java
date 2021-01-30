package com.youngtao.opc.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@Component
@ConfigurationProperties(prefix = "youngtao.alipay")
@Data
public class AlipayConfig {
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String sysServiceProviderId;

    /**
     * 商户私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String publicKey;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 支付宝开放安全地址
     */
    private String gatewayUrl;

    /**
     * 编码
     */
    private String charset;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 订单完成后返回的页面
     */
    private String returnUrl;

    /**
     * 类型
     */
    private String format;

    @Bean
    public AlipayClient alipay() {
        return new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
    }
}
