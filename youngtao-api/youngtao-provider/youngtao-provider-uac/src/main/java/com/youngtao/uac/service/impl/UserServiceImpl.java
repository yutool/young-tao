package com.youngtao.uac.service.impl;

import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.UpdateUserInfoRequest;
import com.youngtao.uac.service.UserService;
import com.youngtao.web.support.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Service
public class UserServiceImpl extends BaseService<UserInfo> implements UserService {
    @Override
    public void updateUserInfo(UpdateUserInfoRequest request) {

    }
}
