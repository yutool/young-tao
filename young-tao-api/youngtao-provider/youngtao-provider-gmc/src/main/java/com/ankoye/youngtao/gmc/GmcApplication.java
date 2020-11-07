package com.ankoye.youngtao.gmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ankoye@qq.com
 * @date 2020/11/07
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmcApplication.class, args);
    }
}
