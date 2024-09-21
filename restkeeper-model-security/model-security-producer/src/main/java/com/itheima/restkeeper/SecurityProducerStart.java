package com.itheima.restkeeper;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.itheima.restkeeper.mapper")
public class SecurityProducerStart {

    public static void main(String[] args) {
        SpringApplication.run(SecurityProducerStart.class, args);
    }

}
