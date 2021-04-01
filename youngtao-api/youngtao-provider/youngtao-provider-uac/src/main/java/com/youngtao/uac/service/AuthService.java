package com.youngtao.uac.service;

import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.domain.UserAuth;
import com.youngtao.uac.model.request.LoginRequest;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
public interface AuthService {

    /**
     * 登录
     */
    AuthToken login(LoginRequest request);

    /**
     *
     * @param userId
     */
    UserAuth getById(String userId);
}
