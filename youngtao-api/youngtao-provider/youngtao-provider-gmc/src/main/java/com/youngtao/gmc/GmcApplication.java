package com.youngtao.gmc;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@SpringBootApplication
@EnableFeignClients(basePackages={"com.youngtao.gsc.api.service", "com.youngtao.uac.api.service"})
public class GmcApplication {
    static {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(GmcApplication.class, args);
    }
}
