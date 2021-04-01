package com.youngtao.uac.controller;

import com.youngtao.uac.common.util.TokenUtils;
import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.domain.UserAuth;
import com.youngtao.uac.model.request.LoginRequest;
import com.youngtao.uac.service.AuthService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public UserAuth getCurrentUser(@RequestHeader("Authorization") String token) {
        Map<String, Object> claims = TokenUtils.parse(token.substring(7));
        String userId = claims.get("userId").toString();
        return authService.getById(userId);
    }

}
