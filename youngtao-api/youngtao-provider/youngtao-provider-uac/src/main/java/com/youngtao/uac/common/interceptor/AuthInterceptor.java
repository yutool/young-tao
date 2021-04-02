package com.youngtao.uac.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.TokenUtils;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfo;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String token = authorization.substring(7);
            Claims claims = TokenUtils.parse(token);
            String userId = claims.get("userId").toString();
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                    .eq("user_id", userId)
            );
            AuthInfo authInfo = BeanUtils.copy(userInfo, AuthInfo.class);
            AuthContext.set(authInfo);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AuthContext.clear();
    }
}
