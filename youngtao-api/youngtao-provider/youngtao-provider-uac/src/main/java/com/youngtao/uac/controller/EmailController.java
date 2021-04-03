package com.youngtao.uac.controller;

import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.model.request.EmailRequest;
import com.youngtao.uac.service.impl.EmailService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.ResponseWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@ResponseWrapper
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisManager<String> redisManager;

    @PostMapping("/register")
    public void sendRegisterCode(@RequestBody EmailRequest request) {
        String code = RandomStringUtils.randomNumeric(6);
        redisManager.set(
                RedisKey.REGISTER_CODE.format(request.getToAddr()),
                code, 5, TimeUnit.MINUTES
        );
        emailService.sendTextMail(request.getToAddr(), "youngtao注册码", code);
    }
}
