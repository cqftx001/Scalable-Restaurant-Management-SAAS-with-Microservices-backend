package com.itheima.restkeeper.config;

import com.itheima.restkeeper.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfig {

    @Autowired
    XxlJobProperties xxlJobExecutorProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobExecutorProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppName(xxlJobExecutorProperties.getAppName());
        xxlJobSpringExecutor.setIp(xxlJobExecutorProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobExecutorProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobExecutorProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobExecutorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobExecutorProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

}
