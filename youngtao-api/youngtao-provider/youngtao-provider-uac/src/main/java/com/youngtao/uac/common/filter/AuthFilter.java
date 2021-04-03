package com.youngtao.uac.common.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.TokenUtils;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.UserInfo;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@Configuration
public class AuthFilter extends OncePerRequestFilter {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        Claims claims = TokenUtils.parse(token);
        String userId = claims.get("userId").toString();
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("user_id", userId)
        );
        AuthInfo authInfo = BeanUtils.copy(userInfo, AuthInfo.class);
        AuthContext.set(authInfo);
        chain.doFilter(request, response);
        AuthContext.clear();
    }
}
