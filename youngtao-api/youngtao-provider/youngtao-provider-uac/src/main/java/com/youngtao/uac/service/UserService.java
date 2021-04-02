package com.youngtao.uac.service;

import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.UpdateUserInfoRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
public interface UserService extends IService<UserInfo> {
    void updateUserInfo(UpdateUserInfoRequest request);
}
