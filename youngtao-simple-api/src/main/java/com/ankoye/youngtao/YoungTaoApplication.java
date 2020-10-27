package com.ankoye.youngtao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class YoungTaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoungTaoApplication.class, args);
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
