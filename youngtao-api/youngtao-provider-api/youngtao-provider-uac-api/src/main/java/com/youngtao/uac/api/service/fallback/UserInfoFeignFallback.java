package com.youngtao.uac.api.service.fallback;

import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.result.RpcResult;
import com.youngtao.uac.api.service.UserInfoFeign;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
public class UserInfoFeignFallback implements UserInfoFeign {
    @Override
    public RpcResult<AuthInfo> getById(String userId) {
        return null;
    }
}
