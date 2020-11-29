package com.youngtao.gateway.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/08
 */
@Component
@ConfigurationProperties(prefix = "youngtao")
public class YoungtaoProperties {

    private List<String> skipPath;

    public List<String> getSkipPath() {
        return skipPath;
    }

    public void setSkipPath(List<String> skipPath) {
        this.skipPath = skipPath;
    }
}
