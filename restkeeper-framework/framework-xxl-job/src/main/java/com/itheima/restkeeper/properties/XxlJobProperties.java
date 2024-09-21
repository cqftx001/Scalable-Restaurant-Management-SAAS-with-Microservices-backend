package com.itheima.restkeeper.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description：自定义rabbit配置文件
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "restkeeper.framework.xxl-job.executor")
public class XxlJobProperties implements Serializable {

    private String adminAddresses;

    private String appName;

    private String ip;

    private Integer port;

    private String accessToken;

    private String logPath;

    private int logRetentionDays;

}
