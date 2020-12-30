package com.youngtao.gmc.config;

import com.youngtao.web.advice.GlobalExceptionAdvice;
import com.youngtao.web.log.GlobalLogAspect;
import com.youngtao.web.advice.ResponseResultAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ankoye@qq.com
 * @date 2020/12/10
 */
@Configuration
public class ContextConfig {

    @Bean
    public GlobalLogAspect globalLogAspect() {
        return new GlobalLogAspect();
    }

    @Bean
    public ResponseResultAdvice responseResultAdvice() {
        return new ResponseResultAdvice();
    }

    @Bean
    public GlobalExceptionAdvice globalExceptionAdvice() {
        return new GlobalExceptionAdvice();
    }
}
