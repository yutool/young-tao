package com.youngtao.gpc;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.youngtao.gmc.api.service", "com.youngtao.uac.api.service"})
public class GpcApplication {
    static  {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(GpcApplication.class);
    }
}
