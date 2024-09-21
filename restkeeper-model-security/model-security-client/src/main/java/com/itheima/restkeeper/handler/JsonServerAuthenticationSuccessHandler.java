package com.itheima.restkeeper.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.UserAdapterFace;
import com.itheima.restkeeper.basic.UserAuth;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.core.JwtTokenManager;
import com.itheima.restkeeper.enums.AuthEnum;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.netty.util.CharsetUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @ClassName JsonServerAuthenticationSuccessHandler.java
 * @Description 登录成功handler
 */
@Component
public class JsonServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    JwtTokenManager jwtTokenManager;

    @DubboReference(version = "${dubbo.application.version}", check = false)
    UserAdapterFace userAdapterFace;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        //指定应答状态
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        //从权限对象中获得认证用户信息
        UserAuth authUser = (UserAuth) authentication.getPrincipal();
        //构建userVo返回对象
        UserVo userVo = UserVo.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .reduceLimit(authUser.getReduceLimit())
                .discountLimit(authUser.getDiscountLimit())
                .enterpriseId(authUser.getEnterpriseId())
                .storeId(authUser.getStoreId())
                .build();
        //处理角色构建
        List<RoleVo> roleByUserId = userAdapterFace.findRoleByUserId(userVo.getId());
        Set<String> roles =  new HashSet<>();
        for (RoleVo roleVo : roleByUserId) {
            roles.add(roleVo.getLabel());
        }
        //处理资源构建
        List<ResourceVo> resourceByUserId = userAdapterFace.findResourceByUserId(userVo.getId());
        Set<String> resources = new HashSet<>();
        for (ResourceVo resourceVo : resourceByUserId) {
            resources.add(resourceVo.getRequestPath());
        }
        //用户指定角色、资源
        userVo.setRoles(roles);
        userVo.setResources(resources);
        //构建JWT令牌
        Map<String,Object> claims = new HashMap<>();
        String userVoJsonString= JSONObject.toJSONString(userVo);
        claims.put("currentUser",userVoJsonString);
        String jwtToken = jwtTokenManager.issuedToken("system",
                jwtTokenManager.getJwtProperties().getTtl(),
                authUser.getId().toString(),
                claims);
        userVo.setJwtToken(jwtToken);
        //返回信息给前端
        ResponseWrap<UserVo> responseWrap = ResponseWrapBuild.build(AuthEnum.SUCCEED, userVo);
        String result = JSONObject.toJSONString(responseWrap);
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
