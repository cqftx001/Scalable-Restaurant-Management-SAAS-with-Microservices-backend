package com.itheima.restkeeper.log.filter;

import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.log.decorator.CacheServerHttpRequestDecorator;
import com.itheima.restkeeper.log.properties.LogProperties;
import com.itheima.restkeeper.log.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @ClassName RequestRecordFilter.java
 * @Description 请求日志拦截
 */
@Component
@EnableConfigurationProperties(LogProperties.class)
public class RequestRecordFilter implements GlobalFilter,Ordered {

    @Autowired
    LogProperties logProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //处理文件上传
        MediaType mediaType =exchange.getRequest().getHeaders().getContentType();
        boolean flag = RequestHelper.isUploadFile(mediaType);
        String path = exchange.getRequest().getURI().getPath();
        List<String> ignoreTestUrl = logProperties.getIgnoreUrl();
        //支持swagger权限校验,忽略请求方式
        for (String testUrl : ignoreTestUrl) {
            if (antPathMatcher.match(testUrl, path)){
                flag = true;
                break;
            }
        }
        if (!flag){
            //1、日志的收集【rabbit的延迟队列进行削峰处理】 2、body体的传递问题【我需要传递当前的exchange的Request】
            CacheServerHttpRequestDecorator cacheServerHttpRequestDecorator
                    = new CacheServerHttpRequestDecorator(exchange.getRequest());
            //把当前的请求体进行改变，用于传递新放入的body
            return chain.filter(exchange.mutate().request(cacheServerHttpRequestDecorator).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
