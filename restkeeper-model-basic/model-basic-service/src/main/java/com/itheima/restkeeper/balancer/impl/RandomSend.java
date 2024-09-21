package com.itheima.restkeeper.balancer.impl;

import com.itheima.restkeeper.balancer.BaseSendLoadBalancer;
import com.itheima.restkeeper.pojo.SmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName Random.java
 * @Description 随机（Random）法
 */
@Component
public class RandomSend extends BaseSendLoadBalancer {

    @Override
    public String chooseChannel(List<SmsTemplate> SmsTemplates, Set<String> mobile) {
        //获得当前模板对应的渠道
        Map<String, String> channelMap = super.getChannelList(SmsTemplates);

        // 取得channel地址List
        Set<String> keySet = channelMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);
        //ArrayList<String> keyList = new ArrayList<String>();
        //keyList.addAll(keySet);

        Random random = new Random();
        int randomPos = random.nextInt(keyList.size()); // 左闭右开 [0,3) 0 1 2
        return keyList.get(randomPos);
    }

}
