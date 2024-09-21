package com.itheima.restkeeper.listen;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.adapter.SmsSendAdapter;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.MqMessage;
import com.itheima.restkeeper.req.SendMessageVo;
import com.itheima.restkeeper.sink.SmsSink;
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
 * @ClassName SmsListen.java
 * @Description 短信监听
 */
@Component
@Slf4j
public class SmsListen {

    @Autowired
    SmsSendAdapter smsSendAdapter;

    @StreamListener(SmsSink.SMS_INPUT)
    public void onMessage(@Payload MqMessage message,
                          @Header(AmqpHeaders.CHANNEL) Channel channel,
                          @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        try {
            String jsonConten = message.getContent();
            log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
            SendMessageVo sendMessageVo = JSONObject.parseObject(jsonConten, SendMessageVo.class);
            boolean flag = smsSendAdapter.SendSms(
                    sendMessageVo.getTemplateNo(),
                    sendMessageVo.getSginNo(),
                    sendMessageVo.getLoadBalancerType(),
                    sendMessageVo.getMobiles(),
                    sendMessageVo.getTemplateParam());
            if (flag){
                channel.basicAck(deliveryTag,false);
            }
        } catch (ProjectException | IOException ex) {
            ex.printStackTrace();
            //第一个参数deliveryTag：发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
            //第二个参数requeue：表示如何处理这条消息，如果值为true，则重新放入RabbitMQ的发送队列，如果值为false，则通知RabbitMQ销毁这条消息
            channel.basicReject(deliveryTag, true);
        }
    }
}
