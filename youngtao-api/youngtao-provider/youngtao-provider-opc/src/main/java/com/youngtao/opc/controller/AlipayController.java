package com.youngtao.opc.controller;

import com.youngtao.opc.common.util.AlipayUtils;
import com.youngtao.opc.model.req.AlipayAppCheckReq;
import com.youngtao.opc.model.req.AlipayReq;
import com.youngtao.opc.service.AlipayService;
import com.youngtao.web.support.NoWrapper;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/24
 */
@ResponseWrapper
@RestController
@RequestMapping("/alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private AlipayUtils alipayUtils;

    @PostMapping("/app")
    public String appPay(@RequestBody AlipayReq request) {
        return alipayService.appPay(request);
    }

    @PostMapping("/web")
    public String webPay(@RequestBody AlipayReq request) {
        return alipayService.webPay(request);
    }

    @PostMapping("/check")
    public boolean check(@RequestBody AlipayAppCheckReq request) {
        return alipayService.check(request);
    }

    @NoWrapper
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        Map<String, String> resultMap = alipayUtils.parseToMap(request);
        // 内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(resultMap)) {
            return alipayService.payNotify(resultMap);
        }
        return "failure";
    }
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//    @Value("${order-pay-topic}")
//    private String payTopic;
//    @GetMapping("/notify/test/{id}")
//    public void test(@PathVariable String id) {
//        OrderPayMsg msg = new OrderPayMsg();
//        msg.setPaymentId(id);
//        msg.setPayType(1);
//        rocketMQTemplate.convertAndSend(RocketMQUtils.withTag(payTopic, MQTagConsts.PAY_SUCCESS), msg);
//    }
}
