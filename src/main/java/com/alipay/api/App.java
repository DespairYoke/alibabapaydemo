package com.alipay.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zwd
 * @date 2018/1/12 10:34
 */

@SpringBootApplication(scanBasePackages = {"com.alipay.api"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
