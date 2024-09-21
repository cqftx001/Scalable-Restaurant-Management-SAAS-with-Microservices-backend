package com.itheima.restkeeper.log.config;

import com.itheima.restkeeper.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SnowflakeIdWorkerConfig.java
 * @Description 唯一键
 */
@Configuration
@EnableConfigurationProperties(SnowflakeIdWorkerProperties.class)
public class SnowflakeIdWorkerConfig {

    @Autowired
    SnowflakeIdWorkerProperties snowflakeIdWorkerProperties;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(
                snowflakeIdWorkerProperties.getWorkerId(),
                snowflakeIdWorkerProperties.getDatacenterId());
    }
}
