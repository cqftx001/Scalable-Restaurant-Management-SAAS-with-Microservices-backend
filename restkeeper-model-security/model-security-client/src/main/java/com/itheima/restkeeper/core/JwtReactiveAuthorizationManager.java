package com.itheima.restkeeper.core;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.properties.SecurityProperties;
import com.itheima.restkeeper.req.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @ClassName AuthorizeConfigManager.java
 * @Description 鉴权用户权限
 */
@Slf4j
@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class JwtReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
                                             AuthorizationContext authorizationContext) {
        //拦截获得url路径
        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("===============进入鉴权url:{}==========",path);
        //jwt令牌校验
        String jwtToken = request.getHeaders().getFirst(SuperConstant.JWT_TOKEN_HEADER);
        boolean flag = jwtTokenManager.isVerifyToken(jwtToken);
        if (!flag){
            return Mono.justOrEmpty(new AuthorizationDecision(false));
        }
        //校验权限
        UserVo userVo = JSONObject
                .parseObject(jwtTokenManager.getCurrentUser(jwtToken).toString(),UserVo.class);
        Set<String> resources = userVo.getResources(); // 当前用户具备权限列表
        AuthorizationDecision authorizationDecision = null;
        //支持restfull
        path = request.getMethodValue() + path;
        for (String resource : resources) {
            if (antPathMatcher.match(resource, path)) {
                log.info("用户请求API校验通过，GrantedAuthority:{}，Path:{} ",resource, path);
                authorizationDecision = new AuthorizationDecision(true);
                return Mono.justOrEmpty(authorizationDecision);
            }
        }
        authorizationDecision = new AuthorizationDecision(false);
        log.info("用户请求API校验未通过，Path:{} ",path);
        return Mono.justOrEmpty(authorizationDecision);
    }

}
