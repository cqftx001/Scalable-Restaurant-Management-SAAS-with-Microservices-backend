package com.itheima.restkeeper.filter;

import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.core.JwtTokenManager;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName AnalysisCurrentUserFilter.java
 * @Description 解析jwtToken为用户json
 */
@Component
@Slf4j
@Order(-1000)
public class AnalysisJwtTokenFilter implements GlobalFilter {

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求头JWT中信息
        String jwtToken = exchange.getRequest().getHeaders().getFirst(SuperConstant.JWT_TOKEN_HEADER);
        //已登录从头部中拿到jwt
        if (!EmptyUtil.isNullOrEmpty(jwtToken)){
            //解析jwt
            String userVoString = jwtTokenManager.getCurrentUser(jwtToken).toString();
            //放入下游请求头中
            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate().header(SuperConstant.CURRENT_USER,userVoString).build();
            //把新的 exchange放回到过滤链
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }
        return chain.filter(exchange);
    }
}
