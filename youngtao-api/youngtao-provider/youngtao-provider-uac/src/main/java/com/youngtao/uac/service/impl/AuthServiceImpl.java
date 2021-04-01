package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.exception.CastException;
import com.youngtao.uac.common.util.TokenUtils;
import com.youngtao.uac.mapper.UserAuthMapper;
import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.domain.UserAuth;
import com.youngtao.uac.model.request.LoginRequest;
import com.youngtao.uac.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Override
    public AuthToken login(LoginRequest request) {
        UserAuth user = userAuthMapper.login(request.getAccount(), request.getPassword());
        if (user == null) {
            CastException.cast("登录失败");
        }
        String token = TokenUtils.generateToken(user.getUserId(), user.getRole());
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(token);
        return authToken;
    }

    @Override
    public UserAuth getById(String userId) {
        return userAuthMapper.selectOne(new QueryWrapper<UserAuth>()
                .eq("user_id", userId)
        );
    }
}
