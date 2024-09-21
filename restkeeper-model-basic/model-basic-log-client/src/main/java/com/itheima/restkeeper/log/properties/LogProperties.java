package com.itheima.restkeeper.log.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TenantProperties.java
 * @Description MyBaits-plus多租户属性
 */
@Slf4j
@ConfigurationProperties(prefix = "restkeeper.framework.log")
public class LogProperties {

    List<String> ignoreUrl = new ArrayList<>();


    public List<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

}
