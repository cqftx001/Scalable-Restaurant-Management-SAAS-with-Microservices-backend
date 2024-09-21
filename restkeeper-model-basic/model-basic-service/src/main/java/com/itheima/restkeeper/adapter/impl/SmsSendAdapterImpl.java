package com.itheima.restkeeper.adapter.impl;

import com.alibaba.fastjson.JSONArray;
import com.itheima.restkeeper.adapter.SmsSendAdapter;
import com.itheima.restkeeper.balancer.SendLoadBalancer;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSendHandler;
import com.itheima.restkeeper.pojo.*;
import com.itheima.restkeeper.req.OtherConfigVo;
import com.itheima.restkeeper.service.ISmsBlacklistService;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.RegisterBeanHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SmsSendAdapterImpl.java
 * @Description 短信适配器实现
 */
@Slf4j
@Component
public class SmsSendAdapterImpl implements SmsSendAdapter {

    @Autowired
    ISmsBlacklistService smsBlacklistService;

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Autowired
    ISmsChannelService smsChannelService;

    @Autowired
    ISmsSignService smsSignService;

    @Autowired
    RegisterBeanHandler registerBeanHandler;

    private static Map<String,String> sendHandlers =new HashMap<>();

    private static Map<String,String> loadBalancers =new HashMap<>();

    static {
        sendHandlers.put(SuperConstant.ALIYUN_SMS,"aliyunSmsSendHandler");
        sendHandlers.put(SuperConstant.TENCENT_SMS,"tencentSmsSendHandler");
        sendHandlers.put(SuperConstant.BAIDU_SMS,"baiduSmsSendHandler");
        loadBalancers.put(SuperConstant.HASH,"hashSend");
        loadBalancers.put(SuperConstant.RANDOM,"randomSend");
        loadBalancers.put(SuperConstant.ROUND_ROBIN,"roundRobinSend2");
        loadBalancers.put(SuperConstant.WEIGHT_RANDOM,"weightRandomSend");
        loadBalancers.put(SuperConstant.WEIGHT_ROUND_ROBIN,"weightRoundRobinSend");
    }

    @Override
    public Boolean SendSms(
        String templateNo,
        String sginNo,
        String loadBalancerType,
        Set<String> mobiles,
        LinkedHashMap<String, String> templateParam) throws ProjectException {
        //过滤黑名单中电话号码
        List<SmsBlacklist> smsBlacklists = smsBlacklistService.list();
        if (!EmptyUtil.isNullOrEmpty(smsBlacklists)){
            Set<String> smsBlackMobiles= smsBlacklists.stream()
                .map(SmsBlacklist::getMobile)
                .collect(Collectors.toSet());
            mobiles.removeAll(smsBlackMobiles);
        }
        Boolean flag = false;
        //获得审核通过模板模板
        List<SmsTemplate> smsTemplates = smsTemplateService.findSmsTemplateByTemplateNo(templateNo);
        if (EmptyUtil.isNullOrEmpty(smsTemplates)){
            log.warn("模板templateNo：{}，不能使用",templateNo);
            return flag;
        }
        //负载均衡器选择对应通道
        String stringLoadBalancers = loadBalancers.get(loadBalancerType);
        SendLoadBalancer sendLoadBalancer = registerBeanHandler.getBean(stringLoadBalancers, SendLoadBalancer.class);
        String channelLabel = sendLoadBalancer.chooseChannel(smsTemplates,mobiles);;
        if (EmptyUtil.isNullOrEmpty(channelLabel)){
            log.error("模板templateNo：{}，负载均衡器未找到对应通道",templateNo);
            return flag;
        }
        //查询渠道
        SmsChannel smsChannel= smsChannelService.findChannelByChannelLabel(channelLabel);
        //选择模板
        SmsTemplate smsTemplate = smsTemplates.stream()
                .filter(n->n.getChannelLabel().equals(smsChannel.getChannelLabel()))
                .findFirst().get();
        //查询签名
        SmsSign smsSign = smsSignService.findSmsSignBySignNoAndChannelLabel(sginNo, channelLabel);
        if (EmptyUtil.isNullOrEmpty(smsSign)){
            log.warn("渠道smsChannel：{}，签名：{}，不能使用",channelLabel,sginNo);
            return flag;
        }
        //应用模板参数转换服务模板参数
        List <OtherConfigVo> otherConfigVos = JSONArray.parseArray(smsTemplate.getOtherConfig(), OtherConfigVo.class);
        LinkedHashMap<String,String> otherConfigVoMap = new LinkedHashMap<>();
        for (OtherConfigVo otherConfigVo : otherConfigVos) {
            otherConfigVoMap.put(otherConfigVo.getConfigKey(),otherConfigVo.getConfigValue());
        }
        LinkedHashMap<String,String> templateParamHandler = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : templateParam.entrySet()) {
            String value = otherConfigVoMap.get(entry.getKey());
            if(!EmptyUtil.isNullOrEmpty(value)){
                templateParamHandler.put(value,entry.getValue());
            }
        }
        //发送短信
        String stringSendHandler = sendHandlers.get(channelLabel);
        SmsSendHandler smsSendHandler =registerBeanHandler.getBean(stringSendHandler,SmsSendHandler.class);
        return smsSendHandler.SendSms(smsTemplate,smsChannel,smsSign,mobiles,templateParamHandler);
    }

    @Override
    public Boolean querySendSms(SmsSendRecord smsSendRecord) throws ProjectException {
        String channelLabel = smsSendRecord.getChannelLabel();
        String stringSendHandler = sendHandlers.get(channelLabel);
        SmsSendHandler smsSendHandler =registerBeanHandler.getBean(stringSendHandler,SmsSendHandler.class);
        return smsSendHandler.querySendSms(smsSendRecord);
    }

    @Override
    public Boolean retrySendSms(SmsSendRecord smsSendRecord) throws ProjectException {
        String channelLabel = smsSendRecord.getChannelLabel();
        String stringSendHandler = sendHandlers.get(channelLabel);
        SmsSendHandler smsSendHandler =registerBeanHandler.getBean(stringSendHandler,SmsSendHandler.class);
        return smsSendHandler.retrySendSms(smsSendRecord);
    }

}
