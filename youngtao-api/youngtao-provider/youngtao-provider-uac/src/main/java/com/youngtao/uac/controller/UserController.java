package com.youngtao.uac.controller;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.uac.model.req.RegisterReq;
import com.youngtao.uac.model.req.ResetPasswordReq;
import com.youngtao.uac.model.req.UpdatePasswordReq;
import com.youngtao.uac.model.req.UpdateUserInfoReq;
import com.youngtao.uac.service.UserService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@ResponseWrapper
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public void register(@RequestBody RegisterReq request) {
        userService.register(request);
    }

    @RequestMapping("/update")
    public void updateUserInfo(@RequestBody UpdateUserInfoReq request) {
        userService.updateUserInfo(request);
    }

    @RequestMapping("/updatePassword")
    public void updatePassword(@RequestBody UpdatePasswordReq request) {
        AuthInfo authInfo = AuthContext.get();
        userService.updatePassword(authInfo.getId(), request);
    }

    @RequestMapping("/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordReq request) {
        userService.resetPassword(request);
    }

}
