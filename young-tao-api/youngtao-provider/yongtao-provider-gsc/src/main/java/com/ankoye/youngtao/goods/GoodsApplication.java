package com.ankoye.youngtao.goods;

import com.ankoye.youngtao.core.result.ResponseResult;
import com.ankoye.youngtao.core.util.MathUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }

    @RequestMapping("/hello")
    public ResponseResult<String> sayHello() {
        String str = MathUtils.randomDigitNumber(6);
        return ResponseResult.success(str);
    }
}
