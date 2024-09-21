package com.itheima.restkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName GateWayStart.java
 * @Description 运营商网关启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ShopStart {

    public static void main(String[] args) {
        SpringApplication.run(ShopStart.class);
    }
}
