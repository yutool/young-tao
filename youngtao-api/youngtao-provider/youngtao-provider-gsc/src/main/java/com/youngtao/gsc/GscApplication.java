package com.youngtao.gsc;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@SpringBootApplication
@EnableScheduling
public class GscApplication {
    static  {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(GscApplication.class);
    }
}