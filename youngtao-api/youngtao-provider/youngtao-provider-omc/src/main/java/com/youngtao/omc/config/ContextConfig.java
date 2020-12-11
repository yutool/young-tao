package com.youngtao.omc.config;

import com.youngtao.web.log.GlobalLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ankoye@qq.com
 * @date 2020/12/11
 */
@Configuration
public class ContextConfig {

    @Bean
    public GlobalLogAspect globalLogAspect() {
        return new GlobalLogAspect();
    }
}
