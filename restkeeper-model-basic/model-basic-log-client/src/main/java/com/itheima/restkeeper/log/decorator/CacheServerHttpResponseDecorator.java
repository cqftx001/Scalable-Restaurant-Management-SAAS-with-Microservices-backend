package com.itheima.restkeeper.log.decorator;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.pojo.MqMessage;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.source.LogSource;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName CacheServerHttpResponseDecorator.java
 * @Description TODO
 */
@Slf4j
public class CacheServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    LogSource logSource;

    ServerWebExchange exchange;

    private Long messageId;

    private String sender;

    private byte[] bytes;

    public CacheServerHttpResponseDecorator(ServerWebExchange exchange,
                                            LogSource logSource,
                                            Long messageId,
                                            String sender) {
        super(exchange.getResponse());
        this.logSource = logSource;
        this.exchange = exchange;
        this.messageId = messageId;
        this.sender =sender;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        AtomicReference<String> bodyRef = new AtomicReference<>();
        if (body instanceof Flux) {
            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
            return super.writeWith(fluxBody.buffer().map(dataBuffer -> {
                DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                DataBuffer join = dataBufferFactory.join(dataBuffer);
                byte[] content = new byte[join.readableByteCount()];
                join.read(content);
                // 释放掉内存
                DataBufferUtils.release(join);
                String result = new String(content, Charset.forName("UTF-8"));
                ResponseWrap responseWrap = JSONObject.parseObject(result,ResponseWrap.class);
                this.trace(exchange.getRequest(),responseWrap);
                byte[] uppedContent = result.getBytes();
                return getDelegate().bufferFactory().wrap(uppedContent);
            }));
        }
        return super.writeWith(body);
    }

    private void trace(ServerHttpRequest request, ResponseWrap responseWrap) {
        //请求IP
        String hostAddress = request.getRemoteAddress().getAddress().getHostAddress();
        //请求host
        String host = request.getURI().getHost();
        //请求路径
        String requestUri = request.getURI().getPath();
        //请求方式
        String method = request.getMethodValue().toUpperCase();
        //请求id
        String requestId = request.getId();
        //请求体
        String requesBody = request.getHeaders().getFirst("requesBody");

        LogBusinessVo logBusinessVo = LogBusinessVo.builder()
                .requestId(requestId)
                .host(host)
                .hostAddress(hostAddress)
                .requestUri(requestUri)
                .requesBody(requesBody)
                .requestMethod(method)
                .responseBody(JSONObject.toJSONString(responseWrap.getDatas()))
                .responseCode(responseWrap.getCode())
                .responseMsg(responseWrap.getMsg())
                .userId(responseWrap.getUserId())
                .userName(responseWrap.getUserName())
                .build();
        String logBusinessVoJsonString = JSONObject.toJSONString(logBusinessVo);
        log.info("================logBusinessVoJsonString:{}",logBusinessVoJsonString);
        //发送队列信息
        MqMessage mqMessage = MqMessage.builder()
                .id(messageId)
                .title("log-message")
                .content(logBusinessVoJsonString)
                .messageType("log-request")
                .produceTime(Timestamp.valueOf(LocalDateTime.now()))
                .sender(sender)
                .build();
        Message<MqMessage> message = MessageBuilder.withPayload(mqMessage).setHeader("type", "log-key").build();
        boolean flag = logSource.logOutput().send(message);
        log.info("发送：{}结果：{}",mqMessage.toString(),flag);
    }
}
