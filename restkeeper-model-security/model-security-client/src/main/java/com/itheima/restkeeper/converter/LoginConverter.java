package com.itheima.restkeeper.converter;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName LoginTypeConverterHandler.java
 * @Description 登录类型转换接口
 */
public interface LoginConverter {

    /***
     * @description 登录转换
     * @param exchange
     * @param loginType
     * @param siteType
     * @return
     */
    public Mono<Authentication> convert(ServerWebExchange exchange,
                                        String loginType,
                                        String siteType);
}
