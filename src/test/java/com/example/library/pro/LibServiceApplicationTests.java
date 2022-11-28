package com.example.library.pro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class LibServiceApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

}
