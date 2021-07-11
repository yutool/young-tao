package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfoDO;
import com.youngtao.uac.model.req.RegisterReq;
import com.youngtao.uac.model.req.ResetPasswordReq;
import com.youngtao.uac.model.req.UpdatePasswordReq;
import com.youngtao.uac.model.req.UpdateUserInfoReq;
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
public class UserServiceImpl extends BaseService<UserInfoDO> implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisManager<String> redisManager;

    @Override
public void updateUserInfo(UpdateUserInfoReq request) {
    UserInfoDO userInfoDO = new UserInfoDO();
    userInfoDO.setUsername(request.getUsername());
    userInfoDO.setGender(request.getGender());
    userInfoDO.setBirthday(request.getBirthday());
    userInfoMapper.update(userInfoDO, new QueryWrapper<UserInfoDO>()
        .eq("user_id", AuthContext.get().getUserId())
    );
}

    @Override
    public void updatePassword(Long id, UpdatePasswordReq request) {
        UserInfoDO userInfoDO = BeanUtils.copy(request, UserInfoDO.class);
        userInfoDO.setId(id);
        userInfoMapper.updateById(userInfoDO);
    }

    @Override
    public void register(RegisterReq request) {
        String code = redisManager.get(RedisKey.REGISTER_USER_CODE.format(request.getEmail()));
        if (!Objects.equals(code, request.getVerifyCode())) {
            CastException.cast("验证码错误");
        }
        UserInfoDO isExist = userInfoMapper.selectByEmail(request.getEmail());
        if (isExist != null) {
            CastException.cast("邮箱已被注册");
        }
        UserInfoDO userInfoDO = BeanUtils.copy(request, UserInfoDO.class);
        userInfoDO.setUsername(request.getName());
        userInfoDO.setUserId(IdUtils.getId("user"));
        userInfoDO.setAvatar("https://avatars.githubusercontent.com/u/56569932?v=4");
        userInfoMapper.insert(userInfoDO);
    }

    @Override
    public void resetPassword(ResetPasswordReq request) {
        String code = redisManager.get(RedisKey.FORGET_PWD_USER_CODE.format(request.getEmail()));
        if (!Objects.equals(code, request.getVerifyCode())) {
            CastException.cast("验证码错误");
        }
        userInfoMapper.resetPassword(request.getEmail(), request.getPassword());
    }
}
