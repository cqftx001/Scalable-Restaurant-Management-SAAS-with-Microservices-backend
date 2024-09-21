package com.itheima.restkeeper.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description：jw配置文件
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "restkeeper.framework.jwt")
public class JwtProperties implements Serializable {

    /**
     * @Description 签名密码
     */
    private String base64EncodedSecretKey;

    /**
     * @Description 有效时间
     */
    private Long ttl;
}
