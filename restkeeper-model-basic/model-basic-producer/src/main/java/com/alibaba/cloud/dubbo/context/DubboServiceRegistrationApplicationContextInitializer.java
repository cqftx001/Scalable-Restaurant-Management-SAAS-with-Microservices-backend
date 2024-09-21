package com.alibaba.cloud.dubbo.context;

import com.alibaba.cloud.dubbo.registry.SpringCloudRegistryFactory;
import com.itheima.restkeeper.binding.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class DubboServiceRegistrationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("DubboServiceRegistrationApplicationContextInitializer init....");
        SpringUtils.tryGetApplicationContext()
                .filter(context -> context instanceof ConfigurableApplicationContext)
                .map(context -> (ConfigurableApplicationContext) context)
                .ifPresent(SpringCloudRegistryFactory::setApplicationContext);
    }
}
