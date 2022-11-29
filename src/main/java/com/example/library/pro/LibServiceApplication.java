package com.example.library.pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class LibServiceApplication {
    //现在直接就是225上13309的端口号
    //socat TCP4-LISTEN:13309,reuseaddr,fork TCP4:192.168.185.60:3308 &
    public static void main(String[] args) {
        SpringApplication.run(LibServiceApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
