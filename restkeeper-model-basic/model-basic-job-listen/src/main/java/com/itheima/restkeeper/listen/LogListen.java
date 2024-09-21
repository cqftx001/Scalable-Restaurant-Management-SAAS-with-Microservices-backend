package com.itheima.restkeeper.listen;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.pojo.LogBusiness;
import com.itheima.restkeeper.pojo.MqMessage;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.service.ILogBusinessService;
import com.itheima.restkeeper.sink.LogSink;
import com.itheima.restkeeper.utils.BeanConv;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName LogListen.java
 * @Description 日志监听
 */
@Slf4j
@Component
public class LogListen {

    @Autowired
    ILogBusinessService logBusinessService;

    @StreamListener(LogSink.LOG_INPUT)
    public void onMessage(@Payload MqMessage message,
                           @Header(AmqpHeaders.CHANNEL) Channel channel,
                           @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        String jsonConten = message.getContent();
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        LogBusinessVo logBusinessVo= JSONObject.parseObject(jsonConten,LogBusinessVo.class);
        boolean flag = logBusinessService.save(BeanConv.toBean(logBusinessVo, LogBusiness.class));
        if (flag){
            channel.basicAck(deliveryTag,false);
        }
    }
}
