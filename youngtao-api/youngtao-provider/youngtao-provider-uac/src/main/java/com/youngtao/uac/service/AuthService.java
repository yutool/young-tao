package com.youngtao.uac.service;

import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.domain.UserInfoDO;
import com.youngtao.uac.model.req.LoginReq;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
public interface AuthService {

    /**
     * 登录
     */
    AuthToken login(LoginReq request);

    /**
     *
     * @param userId
     */
    UserInfoDO getById(String userId);

}
