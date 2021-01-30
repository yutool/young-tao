package com.youngtao.opc.controller;

import com.youngtao.opc.common.constant.AlipayEnum;
import com.youngtao.opc.common.util.AlipayUtils;
import com.youngtao.opc.model.request.AlipayAppRequest;
import com.youngtao.opc.service.AlipayService;
import com.youngtao.web.support.NoWrapper;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/app")
    public String appPay(@RequestBody AlipayAppRequest request) {
        return alipayService.appPay(request);
    }

    @NoWrapper
    @RequestMapping("/notify")
    public ResponseEntity<Object> notify(HttpServletRequest request) {
        // 内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request)) {
            //交易状态
            String tradeStatus = alipayUtils.getParam(request, "trade_status");
            // 商户订单号
            String outTradeNo = alipayUtils.getParam(request, "out_trade_no");
            //支付宝交易号
            String tradeNo = alipayUtils.getParam(request, "trade_no");
            //付款金额
            String totalAmount = alipayUtils.getParam(request, "total_amount");
            //验证
            if (tradeStatus.equals(AlipayEnum.SUCCESS.getValue()) || tradeStatus.equals(AlipayEnum.FINISHED.getValue())) {
                // 验证通过后应该根据业务需要处理订单
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
