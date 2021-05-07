package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.RegisterRequest;
import com.youngtao.uac.model.request.ResetPasswordRequest;
import com.youngtao.uac.model.request.UpdatePasswordRequest;
import com.youngtao.uac.model.request.UpdateUserInfoRequest;
import com.youngtao.uac.service.UserService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Service
public class UserServiceImpl extends BaseService<UserInfo> implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisManager<String> redisManager;

    @Override
public void updateUserInfo(UpdateUserInfoRequest request) {
    UserInfo userInfo = new UserInfo();
    userInfo.setUsername(request.getUsername());
    userInfo.setGender(request.getGender());
    userInfo.setBirthday(request.getBirthday());
    userInfoMapper.update(userInfo, new QueryWrapper<UserInfo>()
        .eq("user_id", AuthContext.get().getUserId())
    );
}

    @Override
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        UserInfo userInfo = BeanUtils.copy(request, UserInfo.class);
        userInfo.setId(id);
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public void register(RegisterRequest request) {
        String code = redisManager.get(RedisKey.REGISTER_USER_CODE.format(request.getEmail()));
        if (!Objects.equals(code, request.getVerifyCode())) {
            CastException.cast("验证码错误");
        }
        UserInfo isExist = userInfoMapper.selectByEmail(request.getEmail());
        if (isExist != null) {
            CastException.cast("邮箱已被注册");
        }
        UserInfo userInfo = BeanUtils.copy(request, UserInfo.class);
        userInfo.setUsername(request.getName());
        userInfo.setUserId(IdUtils.getId("user"));
        userInfo.setAvatar("https://avatars.githubusercontent.com/u/56569932?v=4");
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        String code = redisManager.get(RedisKey.FORGET_PWD_USER_CODE.format(request.getEmail()));
        if (!Objects.equals(code, request.getVerifyCode())) {
            CastException.cast("验证码错误");
        }
        userInfoMapper.resetPassword(request.getEmail(), request.getPassword());
    }
}
