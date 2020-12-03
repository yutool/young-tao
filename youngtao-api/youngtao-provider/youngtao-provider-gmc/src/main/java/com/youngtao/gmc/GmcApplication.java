package com.youngtao.gmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@SpringBootApplication
@EnableFeignClients
public class GmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmcApplication.class, args);
    }
}
