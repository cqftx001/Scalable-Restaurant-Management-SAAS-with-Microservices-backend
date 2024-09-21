package com.itheima.restkeeper.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.properties.SwaggerConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfigProperties.class)
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Autowired
    SwaggerConfigProperties swaggerConfigProperties;

    @Bean
    @ConditionalOnClass(SwaggerConfigProperties.class)
    public Docket createRestApi() {
        // 构建API文档  文档类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 配置 api扫描路径
                .apis(RequestHandlerSelectors.basePackage(swaggerConfigProperties.getSwaggerPath()))
                // 指定路径的设置  any代表所有路径
                .paths(PathSelectors.any())
                // api的基本信息
                .build().apiInfo(new ApiInfoBuilder()
                        // api文档名称
                        .title(swaggerConfigProperties.getTitle())
                        // api文档描述
                        .description(swaggerConfigProperties.getDescription())
                        // api文档版本
                        .version("1.0") // 版本
                        // api作者信息
                        .contact(new Contact(
                                swaggerConfigProperties.getContactName(),
                                swaggerConfigProperties.getContactUrl(),
                                swaggerConfigProperties.getContactEmail()))
                        .build());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

}
