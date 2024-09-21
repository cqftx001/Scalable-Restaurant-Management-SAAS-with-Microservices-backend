package com.itheima.restkeeper.handler.tencent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSendEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSendHandler;
import com.itheima.restkeeper.handler.tencent.config.TencentSmsConfig;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.OtherConfigVo;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName TencentSmsSendHandlerImpl.java
 * @Description 腾讯云邮件发送处理器接口
 */
@Slf4j
@Service("tencentSmsSendHandler")
public class TencentSmsSendHandlerImpl implements SmsSendHandler {

    @Lazy
    @Autowired
    TencentSmsConfig tencentSmsConfig;

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
        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest request = new SendSmsRequest();
        //接收短信的手机号码，Array of String数组格式。
        request.setPhoneNumberSet(mobiles.stream().toArray(String[]::new));
        //短信SdkAppId在短信控制台 添加应用后生成的实际 SdkAppId，
        List <OtherConfigVo> otherConfigVoList = JSONArray.parseArray(smsChannel.getOtherConfig(),OtherConfigVo.class);
        for (OtherConfigVo otherConfigVo : otherConfigVoList) {
            if ("smsSdkAppId".equals(otherConfigVo.getConfigKey())){
                request.setSmsSdkAppId(otherConfigVo.getConfigValue());
                break;
            }
        }
        //模板ID必须填写已审核通过的模板 ID
        request.setTemplateId(smsTemplate.getTemplateCode());
        //短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名
        request.setSignName(smsSign.getSignName());
        //模板参数，若无模板参数，则设置为空。
        if (!EmptyUtil.isNullOrEmpty(templateParam)){
            LinkedList<String> templateParamList = new LinkedList<>();
            for (Map.Entry<String,String> result:templateParam.entrySet()){
                templateParamList.add(result.getValue());
            }
            request.setTemplateParamSet(templateParamList.stream().toArray(String[]::new));
        }
        //返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        SendSmsResponse response = null;
        try {
            response = smsClient.SendSms(request);
        } catch (TencentCloudSDKException e) {
            log.error("腾讯云发送短信：{}，失败:{}",request.toString(), ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendEnum.SEND_FAIL);
        }
        SendStatus[] sendStatusSet = response.getSendStatusSet();
        String content = smsTemplate.getContent();
        for (Map.Entry<String, String> entry : templateParam.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        List<SmsSendRecord> sendRecords = new ArrayList<>();
        for (SendStatus sendStatusHandler : sendStatusSet) {
            String code = sendStatusHandler.getCode();
            String acceptStatus = null;
            String acceptMsg = null;
            String sendStatus = null;
            String sendMsg = null;
            if ("Ok".equals(code)){
                //受理成功
                acceptStatus = SuperConstant.YES;
                acceptMsg = "受理成功";
                sendStatus = SuperConstant.SENDING;
                sendMsg = "发送中";
            }else {
                //受理失败
                acceptStatus=SuperConstant.NO;
                acceptMsg = sendStatusHandler.getMessage();
                sendStatus = SuperConstant.NO;
                sendMsg = "发送失败";
            }
            SmsSendRecord smsSendRecord = SmsSendRecord.builder()
                .sendContent(content)
                .channelLabel(smsChannel.getChannelLabel())
                .channelName(smsChannel.getChannelName())
                .acceptStatus(acceptStatus)
                .acceptMsg(acceptMsg)
                .sendStatus(sendStatus)
                .sendMsg(sendMsg)
                .mobile(sendStatusHandler.getPhoneNumber())
                .signCode(smsSign.getSignCode())
                .signName(smsSign.getSignName())
                .templateNo(smsTemplate.getTemplateNo())
                .templateNo(smsTemplate.getTemplateNo())
                .templateCode(smsTemplate.getTemplateCode())
                .templateId(smsTemplate.getId())
                .templateType(smsTemplate.getSmsType())
                .serialNo(sendStatusHandler.getSerialNo())
                .templateParams(JSONObject.toJSONString(templateParam))
                .build();
            sendRecords.add(smsSendRecord);
        }
        return smsSendRecordService.saveBatch(sendRecords);
    }

    @Override
    public Boolean querySendSms(SmsSendRecord smsSendRecord) throws ProjectException {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        PullSmsSendStatusByPhoneNumberRequest request = new PullSmsSendStatusByPhoneNumberRequest();
        //拉取起始时间，UNIX 时间戳（时间：秒）。
        //注：最大可拉取当前时期前7天的数据。
        request.setBeginTime(smsSendRecord.getCreatedTime().getTime()/1000);
        //偏移量。
        //注：目前固定设置为0。
        request.setOffset(0L);
        //拉取最大条数，最多 100。
        request.setLimit(100L);
        //短信SdkAppId在短信控制台 添加应用后生成的实际 SdkAppId，
        SmsChannel smsChannel = smsChannelService.findChannelByChannelLabel(smsSendRecord.getChannelLabel());
        List <OtherConfigVo> otherConfigVoList = JSONArray.parseArray(smsChannel.getOtherConfig(),OtherConfigVo.class);
        for (OtherConfigVo otherConfigVo : otherConfigVoList) {
            if ("smsSdkAppId".equals(otherConfigVo.getConfigKey())){
                request.setSmsSdkAppId(otherConfigVo.getConfigValue());
                break;
            }
        }
        //下发目的手机号码
        request.setPhoneNumber(smsSendRecord.getMobile());
        //拉取截止时间，UNIX 时间戳（时间：秒）。
        request.setEndTime(new Date().getTime()/1000);
        // 返回的resp是一个PullSmsSendStatusByPhoneNumberResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        PullSmsSendStatusByPhoneNumberResponse response = null;
        try {
            response = smsClient.PullSmsSendStatusByPhoneNumber(request);
        } catch (TencentCloudSDKException e) {
            log.error("腾讯云查询短信发送状态：{}，失败",request.toString());
            throw new ProjectException(SmsSendEnum.QUERY_FAIL);
        }
        PullSmsSendStatus[] pullSmsSendStatusSet = response.getPullSmsSendStatusSet();
        for (PullSmsSendStatus pullSmsSendStatus : pullSmsSendStatusSet) {
            if (pullSmsSendStatus.getSerialNo().equals(smsSendRecord.getSerialNo())){
                if ("SUCCESS".equals(pullSmsSendStatus.getReportStatus())){
                    smsSendRecord.setSendStatus(SuperConstant.YES);
                    smsSendRecord.setSendMsg("发送成功");
                    return smsSendRecordService.updateById(smsSendRecord);
                }else if ("FAIL".equals(pullSmsSendStatus.getReportStatus())){
                    smsSendRecord.setSendStatus(SuperConstant.NO);
                    smsSendRecord.setSendMsg("发送失败");
                    return smsSendRecordService.updateById(smsSendRecord);
                }else {
                    log.info("短信：{}，等待回执!",smsSendRecord.toString());
                }
                break;
            }
        }
        return true;
    }

    @Override
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
