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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @ClassName JsonServerLogoutSuccessHandler.java
 * @Description 退出成功
 */
@Component
public class JsonServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        ResponseWrap<Boolean> responseWrap = ResponseWrapBuild.build(AuthEnum.SUCCEED, true);
        String result = JSONObject.toJSONString(responseWrap);
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}