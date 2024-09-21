package com.itheima.restkeeper;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@SpringBootApplication
@EnableBinding
@EnableDiscoveryClient
@MapperScan(basePackages = "com.itheima.restkeeper.mapper")
public class BasicProductStart {

    public static void main(String[] args) {
        SpringApplication.run(BasicProductStart.class, args);
    }

}
