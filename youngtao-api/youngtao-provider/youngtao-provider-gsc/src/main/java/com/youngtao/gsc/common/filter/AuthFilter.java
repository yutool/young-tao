package com.youngtao.gsc.common.filter;

import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.TokenUtils;
import com.youngtao.uac.api.service.UserInfoFeign;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ankoye@qq.com
 * @date 2021/04/03
 */
@Configuration
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserInfoFeign userInfoFeign;

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
        RpcResult<AuthInfo> userResult = userInfoFeign.getById(userId);
        AuthInfo authInfo = BeanUtils.copy(userResult.getData(), AuthInfo.class);
        AuthContext.set(authInfo);
        chain.doFilter(request, response);
        AuthContext.clear();
    }
}
