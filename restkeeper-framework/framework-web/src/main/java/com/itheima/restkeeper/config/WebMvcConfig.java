package com.itheima.restkeeper.config;

import com.itheima.restkeeper.intercept.TenantIntercept;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName WebMvcConfig.java
 * @Description webMvc高级配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    TenantIntercept tenantIntercept;

    /**
     * @Description 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多租户拦截
        registry.addInterceptor(tenantIntercept).addPathPatterns("/**");
    }
}
