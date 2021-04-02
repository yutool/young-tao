package com.youngtao.uac.controller.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.uac.api.service.UserInfoFeign;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@RestController
public class UserInfoFeignClient implements UserInfoFeign {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public RpcResult<AuthInfo> getById(String userId) {
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("user_id", userId)
        );
        AuthInfo authInfo = BeanUtils.copy(userInfo, AuthInfo.class);
        return RpcResult.success(authInfo);
    }
}
