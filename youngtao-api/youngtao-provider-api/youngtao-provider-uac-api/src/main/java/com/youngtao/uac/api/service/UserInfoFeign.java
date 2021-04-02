package com.youngtao.uac.api.service;

import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.result.RpcResult;
import com.youngtao.uac.api.service.fallback.UserInfoFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@FeignClient(value = "youngtao-uac-serve", contextId = "userInfo", fallback = UserInfoFeignFallback.class)
public interface UserInfoFeign {

    @GetMapping("/api/uac/getById/{userId}")
    RpcResult<AuthInfo> getById(@PathVariable("userId") String userId);
}
