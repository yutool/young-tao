package com.youngtao.uac.service;

import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.RegisterRequest;
import com.youngtao.uac.model.request.ResetPasswordRequest;
import com.youngtao.uac.model.request.UpdatePasswordRequest;
import com.youngtao.uac.model.request.UpdateUserInfoRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
public interface UserService extends IService<UserInfo> {
    /**
     * 更新用户信息
     * @param request
     */
    void updateUserInfo(UpdateUserInfoRequest request);

    /**
     * 更新密码
     * @param id
     * @param request
     */
    void updatePassword(Long id, UpdatePasswordRequest request);

    /**
     * 注册
     * @param request
     */
    void register(RegisterRequest request);

    /**
     * 忘记密码
     * @param request
     */
    void resetPassword(ResetPasswordRequest request);
}
