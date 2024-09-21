package com.itheima.restkeeper.log.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SnowflakeIdWorkerProperties.java
 * @Description 雪花算法配置
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "restkeeper.framework.snowflake")
public class SnowflakeIdWorkerProperties {

    private Long workerId;

    private Long datacenterId;

}
