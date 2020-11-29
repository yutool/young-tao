package com.youngtao.gateway.app.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author ankoye@qq.com
 * @date 2020/11/08
 */
@Configuration
public class LimitConfig {

    /**
     * 根据ip限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest().getRemoteAddress().getHostString();
            return Mono.just(ip);
        };
    }
}