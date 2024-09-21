package com.itheima.restkeeper.balancer;

import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName abSendLoadBalancer.java
 * @Description TODO
 */
@Component
public class BaseSendLoadBalancer implements SendLoadBalancer {

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Autowired
    ISmsChannelService smsChannelService;

    @Override
    public String chooseChannel(List<SmsTemplate> SmsTemplates, Set<String> mobile) {
        return null;
    }

    @Override
    public Map<String, String> getChannelList(List<SmsTemplate> smsTemplates) {
        //处理模板
        Set<String> channelLabelList = smsTemplates.stream()
                .map(SmsTemplate::getChannelLabel).collect(Collectors.toSet());
        //查询模板对应的渠道
        List<SmsChannel> smsChannels =smsChannelService.findChannelInChannelLabel(channelLabelList);
        if (!EmptyUtil.isNullOrEmpty(smsChannels)){
            return smsChannels.stream()
                .collect(Collectors.toMap(SmsChannel::getChannelLabel, SmsChannel::getLevel));
        }
        return null;
    }
}
