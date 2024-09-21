package com.itheima.restkeeper.log.filter;

import com.itheima.restkeeper.log.decorator.CacheServerHttpResponseDecorator;
import com.itheima.restkeeper.log.properties.LogProperties;
import com.itheima.restkeeper.log.utils.RequestHelper;
import com.itheima.restkeeper.source.LogSource;
import com.itheima.restkeeper.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @ClassName ResponseRecordFilter.java
 * @Description Response记录过滤
 */
@Component
@Slf4j
public class ResponseRecordFilter implements GlobalFilter, Ordered {

    @Autowired
    private LogSource logSource;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    LogProperties logProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String port;

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
            CacheServerHttpResponseDecorator serverHttpResponseDecorator =
                    new CacheServerHttpResponseDecorator(
                            exchange,
                            logSource,
                            snowflakeIdWorker.nextId(),
                            applicationName+":"+port);
            return chain.filter(exchange.mutate().response(serverHttpResponseDecorator).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
