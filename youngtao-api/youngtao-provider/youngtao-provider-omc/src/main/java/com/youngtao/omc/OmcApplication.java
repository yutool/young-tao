package com.youngtao.omc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.youngtao.gmc.api.service"})
public class OmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmcApplication.class, args);
    }
}
