package com.youngtao.uac.service.impl;

import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.RegisterRequest;
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
    public void updateUserInfo(Long id, UpdateUserInfoRequest request) {
        UserInfo userInfo = BeanUtils.copy(request, UserInfo.class);
        userInfo.setId(id);
        userInfoMapper.updateById(userInfo);
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
        userInfo.setAvatar("http://www.course.ankoye.com/users/1PUQx4zeqoYgHSHE/avatar_20200725022828.jpeg");
        userInfoMapper.insert(userInfo);
    }
}
