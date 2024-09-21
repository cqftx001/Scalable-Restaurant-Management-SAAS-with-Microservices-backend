package com.itheima.restkeeper.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName ShowApiProperties.java
 * @Description 万维易源应用和秘钥
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "restkeeper.framework.show-api")
public class ShowApiProperties {

    //应用秘钥
    private String sign;

    //应用Id
    private String appid;
}
