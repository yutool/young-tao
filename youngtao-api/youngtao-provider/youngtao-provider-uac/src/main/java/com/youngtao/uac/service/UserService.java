package com.youngtao.uac.service;

import com.youngtao.uac.model.domain.UserInfoDO;
import com.youngtao.uac.model.req.RegisterReq;
import com.youngtao.uac.model.req.ResetPasswordReq;
import com.youngtao.uac.model.req.UpdatePasswordReq;
import com.youngtao.uac.model.req.UpdateUserInfoReq;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
public interface UserService extends IService<UserInfoDO> {
    /**
     * 更新用户信息
     * @param request
     */
    void updateUserInfo(UpdateUserInfoReq request);

    /**
     * 更新密码
     * @param id
     * @param request
     */
    void updatePassword(Long id, UpdatePasswordReq request);

    /**
     * 注册
     * @param request
     */
    void register(RegisterReq request);

    /**
     * 忘记密码
     * @param request
     */
    void resetPassword(ResetPasswordReq request);
}
