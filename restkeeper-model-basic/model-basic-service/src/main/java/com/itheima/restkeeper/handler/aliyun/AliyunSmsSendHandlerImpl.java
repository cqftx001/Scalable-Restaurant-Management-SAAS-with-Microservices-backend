package com.itheima.restkeeper.handler.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSendEnum;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSendHandler;
import com.itheima.restkeeper.handler.aliyun.config.AliyunSmsConfig;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName SmsSendAdapter.java
 * @Description 阿里云邮件发送处理器接口
 */
@Slf4j
@Service("aliyunSmsSendHandler")
public class AliyunSmsSendHandlerImpl implements SmsSendHandler {

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

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
        if (mobiles.size()>1000){
            throw new ProjectException(SmsSendEnum.PEXCEED_THE_LIMIT);
        }
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        //接收短信的手机号码，JSON数组格式。
        request.setPhoneNumberJson(JSONObject.toJSONString(mobiles));
        //请在控制台国内消息或国际/港澳台消息页面中的签名管理页签下签名名称一列查看
        String signCode = smsSign.getSignCode();
        List<String> signNames = new ArrayList<>();
        for (String mobile : mobiles) {
            signNames.add(signCode);
        }
        request.setSignNameJson(JSONObject.toJSONString(signNames));
        //请在控制台国内消息或国际/港澳台消息页面中的签名管理页签下签名名称一列查看。
        request.setTemplateCode(smsTemplate.getTemplateCode());
        //短信模板变量对应的实际值，JSON格式。
        List<Map<String, String>> templateParams = new ArrayList<>();
        for (String mobile : mobiles) {
            templateParams.add(templateParam);
        }
        request.setTemplateParamJson(JSONObject.toJSONString(templateParams));
        Client client =aliyunSmsConfig.queryClient();
        SendBatchSmsResponse response = null;
        try {
            response = client.sendBatchSms(request);
        } catch (Exception e) {
            log.error("阿里云发送短信：{}，失败",request.toString());
            throw new ProjectException(SmsSendEnum.SEND_FAIL);
        }
        //返回请求状态
        String code = response.getBody().getCode();
        String acceptStatus = null;
        String acceptMsg = null;
        String sendStatus = null;
        String sendMsg = null;
        if ("OK".equals(code)){
            //受理成功
            acceptStatus = SuperConstant.YES;
            acceptMsg = "受理成功";
            sendStatus = SuperConstant.SENDING;
            sendMsg = "发送中";
        }else {
            //受理失败
            acceptStatus=SuperConstant.NO;
            acceptMsg = response.getBody().getMessage();
            sendStatus = SuperConstant.NO;
            sendMsg = "发送失败";
        }
        //构建发送记录
        String message = response.getBody().getMessage();
        String bizId = response.getBody().getBizId();
        String content = smsTemplate.getContent();
        for (Map.Entry<String, String> entry : templateParam.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        List<SmsSendRecord> sendRecords = new ArrayList<>();
        for (String mobile : mobiles) {
            SmsSendRecord smsSendRecord = SmsSendRecord.builder()
            .sendContent(content)
            .channelLabel(smsChannel.getChannelLabel())
            .channelName(smsChannel.getChannelName())
            .acceptStatus(acceptStatus)
            .acceptMsg(acceptMsg)
            .sendStatus(sendStatus)
            .sendMsg(sendMsg)
            .mobile(mobile)
            .signCode(smsSign.getSignCode())
            .signName(smsSign.getSignName())
            .templateNo(smsTemplate.getTemplateNo())
            .templateCode(smsTemplate.getTemplateCode())
            .templateId(smsTemplate.getId())
            .templateType(smsTemplate.getSmsType())
            .serialNo(bizId)
            .templateParams(JSONObject.toJSONString(templateParam))
            .build();
            sendRecords.add(smsSendRecord);
        }
        return smsSendRecordService.saveBatch(sendRecords);
    }

    @Override
    public Boolean querySendSms(SmsSendRecord smsSendRecord) throws ProjectException {
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setBizId(smsSendRecord.getSerialNo());
        request.setPhoneNumber(smsSendRecord.getMobile());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(simpleDateFormat.format(smsSendRecord.getCreatedTime()));
        request.setPageSize(50L);
        request.setCurrentPage(1L);
        // 复制代码运行请自行打印 API 的返回值
        Client client =aliyunSmsConfig.queryClient();
        QuerySendDetailsResponse response = null;
        try {
            response = client.querySendDetails(request);
        } catch (Exception e) {
            log.error("阿里云查询短信发送状态：{}，失败",request.toString());
            throw new ProjectException(SmsSendEnum.QUERY_FAIL);
        }

        String code = response.getBody().getCode();
        //处理结果
        if ("OK".equals(code)){
            QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOsSmsSendDetailDTO result =
                    response.getBody().getSmsSendDetailDTOs().getSmsSendDetailDTO().stream()
                    .filter(n -> n.getPhoneNum().equals(smsSendRecord.getMobile())
                        && n.getTemplateCode().equals(smsSendRecord.getTemplateCode()))
                    .findFirst().get();
            if (result.getSendStatus()==3){
                smsSendRecord.setSendStatus(SuperConstant.YES);
                smsSendRecord.setSendMsg("发送成功");
                return smsSendRecordService.updateById(smsSendRecord);
            }else if (result.getSendStatus()==2){
                smsSendRecord.setSendStatus(SuperConstant.NO);
                smsSendRecord.setSendMsg("发送失败");
                return smsSendRecordService.updateById(smsSendRecord);
            }else {
                log.info("短信：{}，等待回执!",smsSendRecord.toString());
            }
        }
        return true;
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
        SmsSign smsSign = smsSignService.findSmsSignBySignCodeAndChannelLabel(
                smsSendRecord.getSignCode(),
                smsSendRecord.getChannelLabel());
        Set<String> mobiles = new HashSet<>();
        mobiles.add(smsSendRecord.getMobile());
        LinkedHashMap<String, String> templateParam = JSON.parseObject(smsSendRecord.getTemplateParams(), LinkedHashMap.class);
        Boolean flag = SendSms(smsTemplate, smsChannel, smsSign, mobiles, templateParam);
        if (flag){
            flag = smsSendRecordService.removeById(smsSendRecord.getId());
        }
        return flag;
    }
}
