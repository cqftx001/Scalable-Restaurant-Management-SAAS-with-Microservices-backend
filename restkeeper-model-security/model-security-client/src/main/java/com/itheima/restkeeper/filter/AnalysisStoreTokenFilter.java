package com.itheima.restkeeper.filter;

import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName AnalysisStoreTokenFilter.java
 * @Description ,解析storeToken向下游业务系统传递
 */
@Component
@Slf4j
@Order(-999)
public class AnalysisStoreTokenFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获得请求头中的store信息
        String storeToken = exchange.getRequest().getHeaders().getFirst(SuperConstant.STORE_TOKEN_HEADER);
        //已登录从头部中拿到storeToken
        if (!EmptyUtil.isNullOrEmpty(storeToken)){
            //放入下游请求头中
            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate().header(SuperConstant.CURRENT_STORE,storeToken).build();
            //把新的 exchange放回到过滤链
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }
        return chain.filter(exchange);
    }
}
