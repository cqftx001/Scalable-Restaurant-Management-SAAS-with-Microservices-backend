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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName JsonServerAccessDeniedHandler.java
 * @Description 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
public class JsonServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        //指定返回状态
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        //返回处理结果
        ResponseWrap<Boolean> responseWrap = ResponseWrapBuild.build(AuthEnum.AUTH_FAIL, false);
        String result = JSONObject.toJSONString(responseWrap);
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
