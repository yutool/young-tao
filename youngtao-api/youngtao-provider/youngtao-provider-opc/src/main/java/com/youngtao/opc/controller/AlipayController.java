package com.youngtao.opc.controller;

import com.youngtao.opc.common.util.AlipayUtils;
import com.youngtao.opc.model.request.AlipayAppCheckRequest;
import com.youngtao.opc.model.request.AlipayRequest;
import com.youngtao.opc.service.AlipayService;
import com.youngtao.web.support.NoWrapper;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public String appPay(@RequestBody AlipayRequest request) {
        return alipayService.appPay(request);
    }

    @PostMapping("/web")
    public String webPay(@RequestBody AlipayRequest request) {
        return alipayService.webPay(request);
    }

    @PostMapping("/check")
    public boolean check(@RequestBody AlipayAppCheckRequest request) {
        return alipayService.check(request);
    }

    @NoWrapper
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request) {
        return "success";
//        Map<String, String> resultMap = alipayUtils.parseToMap(request);
//        // 内容验签，防止黑客篡改参数
//        if (alipayUtils.rsaCheck(resultMap)) {
//            return alipayService.payNotify(resultMap);
//        }
//        return "failure";
    }
}
