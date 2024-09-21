package com.itheima.restkeeper.handler.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.SendMessageItem;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSendEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSendHandler;
import com.itheima.restkeeper.handler.baidu.config.BaiduSmsConfig;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName BaiduSmsSendHandlerImpl.java
 * @Description 百度短信发送
 */
@Slf4j
@Service("baiduSmsSendHandler")
public class BaiduSmsSendHandlerImpl implements SmsSendHandler {

    @Autowired
    BaiduSmsConfig baiduSmsConfig;

    @Autowired
    ISmsSendRecordService smsSendRecordService;

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Autowired
    ISmsChannelService smsChannelService;

    @Autowired
    ISmsSignService smsSignService;

    @Override
    public Boolean SendSms(SmsTemplate smsTemplate,
                           SmsChannel smsChannel,
                           SmsSign smsSign,
                           Set<String> mobiles,
                           LinkedHashMap<String, String> templateParam) throws ProjectException {
        //超过发送上限
        if (mobiles.size() > 200) {
            throw new ProjectException(SmsSendEnum.PEXCEED_THE_LIMIT);
        }
        //实例化请求对象
        SendMessageV3Request request = new SendMessageV3Request();
        //手机号码,支持单个或多个手机号，多个手机号之间以英文逗号分隔，
        // 一次请求最多支持200个手机号。国际/港澳台号码请按照E.164规范表示，
        // 例如台湾手机号以+886开头，”+“不能省略。
        request.setMobile(JSONObject.toJSONString(mobiles)
                .replace("\"","")
                .replace("[","")
                .replace("]",""));
        //模板id
        request.setTemplate(smsTemplate.getTemplateCode());
        //签名Id
        request.setSignatureId(smsSign.getSignCode());
        request.setContentVar(templateParam);
        SmsClient client = baiduSmsConfig.queryClient();
        SendMessageV3Response response = client.sendMessage(request);
        //处理返回结果
        String content = smsTemplate.getContent();
        for (Map.Entry<String, String> entry : templateParam.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        List<SmsSendRecord> sendRecords = new ArrayList<>();
        List<SendMessageItem> sendMessageItems = response.getData();
        for (SendMessageItem sendMessageItem : sendMessageItems) {
            String acceptStatus = null;
            String acceptMsg = null;
            String sendStatus = null;
            String sendMsg = null;
            if ("1000".equals(sendMessageItem.getCode())){
                //受理成功
                acceptStatus = SuperConstant.YES;
                acceptMsg = "受理成功";
                sendStatus = SuperConstant.SENDING;
                sendMsg = "发送中";
            }else {
                //受理失败
                acceptStatus=SuperConstant.NO;
                acceptMsg = response.getMessage();
            }
            SmsSendRecord smsSendRecord = SmsSendRecord.builder()
                .sendContent(content)
                .channelLabel(smsChannel.getChannelLabel())
                .channelName(smsChannel.getChannelName())
                .acceptStatus(acceptStatus)
                .acceptMsg(acceptMsg)
                .sendStatus(sendStatus)
                .sendMsg(sendMsg)
                .mobile(sendMessageItem.getMobile())
                .signCode(smsSign.getSignCode())
                .signName(smsSign.getSignName())
                .templateNo(smsTemplate.getTemplateNo())
                .templateCode(smsTemplate.getTemplateCode())
                .templateId(smsTemplate.getId())
                .templateType(smsTemplate.getSmsType())
                .templateParams(JSONObject.toJSONString(templateParam))
                .build();
            sendRecords.add(smsSendRecord);
        }
        return smsSendRecordService.saveBatch(sendRecords);
    }

    @Override
    public Boolean querySendSms(SmsSendRecord smsSendRecord)throws ProjectException {
        log.warn("百度简单短信无主动轮询接口");
        return false;
    }

    @Override
    @Transactional
    public Boolean retrySendSms(SmsSendRecord smsSendRecord) throws ProjectException {
        //已发送，发送中的短信不处理
        if (smsSendRecord.getSendStatus().equals(SuperConstant.SENDING)||
                smsSendRecord.getSendStatus().equals(SuperConstant.YES)) {
            throw new ProjectException(SmsSendEnum.SEND_SUCCEED);
        }
        SmsTemplate smsTemplate = smsTemplateService.getById(smsSendRecord.getTemplateId());
        SmsChannel smsChannel = smsChannelService.findChannelByChannelLabel(smsSendRecord.getChannelLabel());
        SmsSign smsSign = smsSignService.findSmsSignBySignCodeAndChannelLabel(smsSendRecord.getSignCode(), smsSendRecord.getChannelLabel());
        Set<String> mobiles = new HashSet<>();
        mobiles.add(smsSendRecord.getMobile());
        LinkedHashMap<String, String> templateParam =
                JSON.parseObject(smsSendRecord.getTemplateParams(), LinkedHashMap.class);
        Boolean flag = SendSms(smsTemplate, smsChannel, smsSign, mobiles, templateParam);
        if (flag){
            flag = smsTemplateService.removeById(smsSendRecord.getTemplateId());
        }
        return flag;
    }
}
