package com.itheima.restkeeper.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description 阿里云OSS上传配置类
 */

@Setter
@Getter
@NoArgsConstructor
@ConfigurationProperties("spring.cloud.alicloud.oss")
public class OssAliyunConfigProperties{

    /**
     * 桶名称
     */
    private String bucketName ;

}
