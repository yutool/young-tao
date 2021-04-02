package com.youngtao.uac.controller;

import com.youngtao.uac.model.request.UpdateUserInfoRequest;
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

    @RequestMapping("/update")
    public void updateUserInfo(@RequestBody UpdateUserInfoRequest request) {
        userService.updateUserInfo(request);
    }

}
