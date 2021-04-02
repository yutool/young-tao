package com.youngtao.uac.controller;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.request.LoginRequest;
import com.youngtao.uac.service.AuthService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@ResponseWrapper
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/login")
    public AuthToken login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @RequestMapping("/currentUser")
    public AuthInfo getCurrentUser() {
        return AuthContext.get();
    }

}
