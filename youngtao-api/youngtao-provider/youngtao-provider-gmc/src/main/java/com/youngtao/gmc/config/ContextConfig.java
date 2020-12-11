package com.youngtao.gmc.config;

import com.youngtao.web.log.GlobalLogAspect;
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
}
