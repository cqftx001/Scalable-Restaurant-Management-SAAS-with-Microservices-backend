package com.itheima.restkeeper.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.basic.UserAuth;
import com.itheima.restkeeper.enums.AuthEnum;
import com.itheima.restkeeper.exception.AuthCodeException;
import com.itheima.restkeeper.exception.UsernamePasswordException;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @ClassName JsonServerAuthenticationFailureHandler.java
 * @Description 登录失败
 */
@Component
public class JsonServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        //指定应答状态
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        ResponseWrap<UserAuth> responseWrap = null;
        if (exception instanceof UsernamePasswordException) {
            responseWrap = ResponseWrapBuild.build(AuthEnum.AUTH_UPWD_FAIL, null);
        }
        if (exception instanceof AuthCodeException) {
            AuthCodeException  ex = (AuthCodeException) exception;
            responseWrap = ResponseWrapBuild.build(ex.getBasicEnum(), null);
        }

        String result = JSONObject.toJSONString(responseWrap);
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
