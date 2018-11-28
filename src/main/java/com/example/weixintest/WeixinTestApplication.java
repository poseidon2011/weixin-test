package com.example.weixintest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeixinTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinTestApplication.class, args);
    }
}
