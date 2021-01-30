package com.youngtao.opc.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.youngtao.opc.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Component
public class AlipayUtils {
    @Autowired
    private AlipayConfig config;

    /**
     * 生成订单号
     */
    public String getOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int a = (int)(Math.random() * 9000.0D) + 1000;
        System.out.println(a);
        Date date = new Date();
        String str = sdf.format(date);
        String[] split = str.split("-");
        String s = split[0] + split[1] + split[2];
        String[] split1 = s.split(" ");
        String s1 = split1[0] + split1[1];
        String[] split2 = s1.split(":");
        return split2[0] + split2[1] + split2[2] + a;
    }

    /**
     * 校验签名
     */
    public boolean rsaCheck(HttpServletRequest request){
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>(1);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            return AlipaySignature.rsaCheckV1(params,
                    config.getPublicKey(),
                    config.getCharset(),
                    config.getSignType());
        } catch (AlipayApiException e) {
            return false;
        }
    }

    public String getParam(HttpServletRequest request, String arg) {
        return new String(request.getParameter(arg).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
