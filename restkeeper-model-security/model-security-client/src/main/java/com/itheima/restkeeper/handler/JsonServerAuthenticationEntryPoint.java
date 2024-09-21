package com.itheima.restkeeper.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.AuthEnum;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName JsonAuthenticationEntryPoint.java
 * @Description  用来解决匿名用户访问无权限资源时的异常
 */
@Component
public class  JsonServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {


    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        //请求状态指定
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        //返回结果封装
        ResponseWrap<Boolean> responseWrap = ResponseWrapBuild.build(AuthEnum.NEED_LOGIN, false);
        String result = JSONObject.toJSONString(responseWrap);
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
