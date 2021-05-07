package com.youngtao.omc;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.youngtao.gmc.api.service",
        "com.youngtao.gsc.api.service",
        "com.youngtao.opc.api.service",
        "com.youngtao.uac.api.service",
})
public class OmcApplication {
    static {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(OmcApplication.class, args);
    }
}
