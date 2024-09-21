package com.itheima.restkeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class TradingProducerStart {

    public static void main(String[] args) {
        SpringApplication.run(TradingProducerStart.class, args);
    }

}
