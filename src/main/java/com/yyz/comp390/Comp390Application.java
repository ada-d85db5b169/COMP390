package com.yyz.comp390;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Comp390Application {

    public static void main(String[] args) {
        SpringApplication.run(Comp390Application.class, args);
        log.info("\n\nApplication Started\n");
    }

}
