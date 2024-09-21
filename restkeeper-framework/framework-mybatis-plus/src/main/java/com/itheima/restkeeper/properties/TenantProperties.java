package com.itheima.restkeeper.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TenantProperties.java
 * @Description MyBaits-plus多租户属性
 */
@Slf4j
@ConfigurationProperties(prefix = "mybatis-plus")
public class TenantProperties {

    //ignore-enterprise-tables:
    List<String> ignoreEnterpriseTables = new ArrayList<>();

    //ignore-store-tables:
    List<String> ignoreStoreTables = new ArrayList<>();

    public List<String> getIgnoreEnterpriseTables() {
        return ignoreEnterpriseTables;
    }

    public void setIgnoreEnterpriseTables(List<String> ignoreEnterpriseTables) {
        this.ignoreEnterpriseTables = ignoreEnterpriseTables;
    }

    public List<String> getIgnoreStoreTables() {
        return ignoreStoreTables;
    }

    public void setIgnoreStoreTables(List<String> ignoreStoreTables) {
        this.ignoreStoreTables = ignoreStoreTables;
    }
}
