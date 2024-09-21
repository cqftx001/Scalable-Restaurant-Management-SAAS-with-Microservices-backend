package com.itheima.restkeeper.face;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.SmsSendFace;
import com.itheima.restkeeper.adapter.SmsSendAdapter;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.MqMessage;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.req.SendMessageVo;
import com.itheima.restkeeper.req.SmsSendRecordVo;
import com.itheima.restkeeper.source.SmsSource;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * @ClassName SmsSendFace.java
 * @Description dubbo短信发送接口
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
        methods ={
                @Method(name = "SendSms",retries = 0),
                @Method(name = "querySendSms",retries = 2),
                @Method(name = "retrySendSms",retries = 0)
        })
public class SmsSendFaceImpl implements SmsSendFace {

    @Autowired
    SmsSendAdapter smsSendAdapter;

    @Autowired
    SmsSource smsSource;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String port;

    @Override
    public Boolean SendSms(SendMessageVo sendMessageVo) throws ProjectException {
        String sendMessageVoString = JSONObject.toJSONString(sendMessageVo);
        //发送队列信息
        MqMessage mqMessage = MqMessage.builder()
                .id((Long)identifierGenerator.nextId(sendMessageVo))
                .title("sms-message")
                .content(sendMessageVoString)
                .messageType("sms-request")
                .produceTime(Timestamp.valueOf(LocalDateTime.now()))
                .sender(applicationName+":"+port)
                .build();
        Message<MqMessage> message = MessageBuilder.withPayload(mqMessage)
                .setHeader("type", "sms-key")
                .build();
        Boolean flag =  smsSource.smsOutput().send(message);
        log.info("发送：{}结果：{}",mqMessage.toString(),flag);
        return flag;
    }

    @Override
    public Boolean querySendSms(SmsSendRecordVo smsSendRecordVo) throws ProjectException {
        SmsSendRecord smsSendRecord = BeanConv.toBean(smsSendRecordVo, SmsSendRecord.class);
        return smsSendAdapter.querySendSms(smsSendRecord);
    }

    @Override
    public Boolean retrySendSms(SmsSendRecordVo smsSendRecord) throws ProjectException {
        return null;
    }
}
