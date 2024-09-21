package com.itheima.restkeeper.converter;

import com.itheima.restkeeper.converter.LoginConverter;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.RegisterBeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName ReactiveFormLoginAuthenticationConverter.java
 * @Description 自定义表单转换
 */
@Component
public class ReactiveServerAuthenticationConverter implements ServerAuthenticationConverter {

    //登录方式
    private String loginTypeParameter = "loginType";

    //站点类型
    private String siteTypeParameter = "siteType";

    @Autowired
    RegisterBeanHandler registerBeanHandler;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String loginType = exchange.getRequest().getHeaders().getFirst("loginType");
        String siteType = exchange.getRequest().getHeaders().getFirst("siteType");
        if (EmptyUtil.isNullOrEmpty(loginType)){
            throw  new BadCredentialsException("客户登陆异常");
        }
        LoginConverter loginConverter = registerBeanHandler.getBean(loginType, LoginConverter.class);
        return loginConverter.convert(exchange,loginType,siteType);
    }
}
