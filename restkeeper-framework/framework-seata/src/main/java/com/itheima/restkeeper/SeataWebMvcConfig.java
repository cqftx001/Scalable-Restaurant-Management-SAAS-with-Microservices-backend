package com.itheima.restkeeper;

import com.alibaba.cloud.seata.web.SeataHandlerInterceptor;
import com.itheima.restkeeper.config.WebMvcConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName SeataWebMvcConfig.java
 * @Description webMvc高级配置
 */
@Configuration
public class SeataWebMvcConfig extends WebMvcConfig {

    /***
     * @description 解决XID传递问题
     * @return
     * @return: com.alibaba.cloud.seata.web.SeataHandlerInterceptor
     */
    @Bean
    public SeataHandlerInterceptor seataHandlerInterceptor(){
        return new SeataHandlerInterceptor();
    }

    /**
     * @Description 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(seataHandlerInterceptor()).addPathPatterns("/**");
    }

}
