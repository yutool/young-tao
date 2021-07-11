package com.youngtao.opc.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.BigDecimals;
import com.youngtao.core.util.DateTimeUtils;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.opc.api.model.constant.PayRecordStatus;
import com.youngtao.opc.common.constant.AlipayEnum;
import com.youngtao.opc.common.constant.MQTagConsts;
import com.youngtao.opc.common.constant.PayRecordType;
import com.youngtao.opc.config.AlipayConfig;
import com.youngtao.opc.mapper.OrderPayRecordMapper;
import com.youngtao.opc.model.domain.OrderPayRecordDO;
import com.youngtao.opc.model.msg.OrderPayMsg;
import com.youngtao.opc.model.req.AlipayAppCheckReq;
import com.youngtao.opc.model.req.AlipayReq;
import com.youngtao.opc.model.req.TradeRefundReq;
import com.youngtao.opc.model.response.TradeRefundRes;
import com.youngtao.opc.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayClient alipay;
    @Autowired
    private AlipayConfig config;
    @Resource
    private OrderPayRecordMapper recordMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Value("${order-pay-topic}")
    private String payTopic;

    private static final String SUCCESS_CODE = "10000";

    @Override
    public String appPay(AlipayReq request) {
        OrderPayRecordDO record = recordMapper.selectByPaymentId(request.getPaymentId());
        if (record == null) {
            CastException.cast("订单不存在");
        }
        if (record.getStatus() == 1) {
            CastException.cast("订单已完成支付");
        }

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(request.getPaymentId());
        model.setSubject(request.getSubject());
        model.setBody(request.getBody());
        model.setTotalAmount(BigDecimals.round(record.getMoney()));
        model.setTimeoutExpress("30m");
        AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
        payRequest.setBizModel(model);
        payRequest.setNotifyUrl(config.getNotifyUrl());
        try {
            return alipay.sdkExecute(payRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            CastException.cast("申请支付失败，请稍后重试");
        }
        return null;
    }

    @Override
    public String webPay(AlipayReq request) {
        OrderPayRecordDO record = recordMapper.selectByPaymentId(request.getPaymentId());
        if (record == null) {
            CastException.cast("订单不存在");
        }
        if (record.getStatus() == 1) {
            CastException.cast("订单已完成支付");
        }

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(request.getPaymentId());
        model.setSubject(request.getSubject());
        model.setBody(request.getBody());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setTotalAmount(BigDecimals.round(record.getMoney()));
        model.setTimeoutExpress("30m");
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setBizModel(model);
        payRequest.setNotifyUrl(config.getNotifyUrl());
        payRequest.setReturnUrl(config.getReturnUrl());
        try {
            return alipay.pageExecute(payRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            CastException.cast("申请支付失败，请稍后重试");
        }
        return null;
    }

    @Override
    public boolean check(AlipayAppCheckReq request) {
        AlipayTradeQueryRequest query = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(request.getPaymentId());
        query.setBizModel(model);
        try {
            AlipayTradeQueryResponse result = alipay.execute(query);
            if (result.getTradeStatus().equals(AlipayEnum.SUCCESS.getValue())) {
                OrderPayMsg msg = new OrderPayMsg();
                msg.setPaymentId(request.getPaymentId());
                rocketMQTemplate.convertAndSend(RocketMQUtils.withTag(payTopic, MQTagConsts.PAY_SUCCESS), msg);
                // 更新数据库
                OrderPayRecordDO record = recordMapper.selectByPaymentId(request.getPaymentId());
                record.setPayType(PayRecordType.ALIPAY_TYPE);
                record.setStatus(PayRecordStatus.PAID);
                record.setPayTime(result.getSendPayDate());
                record.setTransactionId(result.getTradeNo());
                recordMapper.updateById(record);
                return true;
            }
        } catch (AlipayApiException e) {
            log.info("alipay check pay status error, exception: ", e);
        }
        return false;
    }

    @Override
    public TradeRefundRes tradeRefund(TradeRefundReq refundRequest) {
        AlipayTradeRefundModel mode = new AlipayTradeRefundModel();
        mode.setOutTradeNo(refundRequest.getOutTradeNo());
        mode.setTradeNo(refundRequest.getTradeNo());
        mode.setRefundAmount(BigDecimals.round(refundRequest.getRefundAmount()));
        mode.setOutRequestNo(refundRequest.getOutRequestNo());
        mode.setRefundReason(refundRequest.getRefundReason());

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(mode);
        try {
            AlipayTradeRefundResponse response = alipay.execute(request);
            if (SUCCESS_CODE.equals(response.getCode())) {
                TradeRefundRes result = new TradeRefundRes();
                result.setOutTradeNo(response.getOutTradeNo());
                result.setTradeNo(response.getTradeNo());
                result.setRefundFee(new BigDecimal(response.getRefundFee()));
                return result;
            } else {
                log.info("tradeRefund fail, response = {}", refundRequest);
                return null;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(Map<String, String> resultMap) {
        String tradeStatus = resultMap.get("trade_status");
        String paymentId = resultMap.get("out_trade_no");
        // 验证
        if (tradeStatus.equals(AlipayEnum.SUCCESS.getValue()) || tradeStatus.equals(AlipayEnum.FINISHED.getValue())) {
            OrderPayRecordDO record = recordMapper.selectByPaymentId(paymentId);
            if (record == null) {
                log.warn("alipay notify exception: record is null");
                return "failure";
            }
            if (record.getStatus() == PayRecordStatus.PAID) {
                return "success";
            }
            // 更新记录
            record.setPayType(PayRecordType.ALIPAY_TYPE);
            record.setStatus(PayRecordStatus.PAID);
            record.setPayTime(DateTimeUtils.formatDateTime(resultMap.get("gmt_payment")));
            record.setTransactionId(resultMap.get("trade_no"));
            recordMapper.updateById(record);
            // 发送MQ
            OrderPayMsg msg = new OrderPayMsg();
            msg.setPaymentId(paymentId);
            msg.setPayType(1);
            rocketMQTemplate.convertAndSend(RocketMQUtils.withTag(payTopic, MQTagConsts.PAY_SUCCESS), msg);
        }
        return "success";
    }
}
