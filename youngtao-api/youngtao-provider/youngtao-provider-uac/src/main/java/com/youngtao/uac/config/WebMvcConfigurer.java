package com.youngtao.uac.config;

import com.youngtao.uac.common.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
