package com.ankoye.youngtao.gateway.app.filter;

import com.ankoye.youngtao.gateway.app.config.YoungtaoProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ankoye@qq.com
 * @date 2020/11/08
 */
@Component
@Order(0)
public class AuthFilter implements GlobalFilter {
    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private YoungtaoProperties youngtaoProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        // 1.放行不需要拦截的请求
        String path = request.getURI().getPath();
        if (isSkipPath(path)){
            //直接放行
            return chain.filter(exchange);
        }

        // 2. 如果无token，尝试重cookie中读取，这里不实现了
        if(StringUtils.isEmpty(token)) {
            // 从cookie中获取jti的值，如果该值不存在拒绝本次访问
            String jwt = "";
            // 从redis中获取jwt的值，如果该值不存在拒绝本次访问

            // 对当前的请求对象进行增强,让它会携带令牌的信息
            request.mutate().header(AUTHORIZE_TOKEN,TOKEN_PREFIX + jwt);
        }

        return chain.filter(exchange);
    }

    private boolean isSkipPath(String path) {
        for (String url : youngtaoProperties.getSkipPath()) {
            if (StringUtils.isBlank(url)) {
                continue;
            }
            if (url.endsWith("/**") && path.startsWith(url.substring(0, url.lastIndexOf("/**")))) {
                return true;
            } else if (url.equals(path)) {
                return true;
            }
        }
        return false;
    }

}

