package com.itheima.restkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName GateWayStart.java
 * @Description 网关启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OperatorStart {

    public static void main(String[] args) {
        SpringApplication.run(OperatorStart.class);
    }
}
