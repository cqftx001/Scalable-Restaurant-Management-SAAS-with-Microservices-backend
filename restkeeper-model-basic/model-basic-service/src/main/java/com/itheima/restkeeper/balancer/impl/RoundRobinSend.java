package com.itheima.restkeeper.balancer.impl;

import com.itheima.restkeeper.balancer.BaseSendLoadBalancer;
import com.itheima.restkeeper.pojo.SmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RoundRobin.java
 * @Description 轮询（Round Robin）法
 * 自定义负载均衡算法：
 * * 防止数组越界 （按位与, 对集合大小取模运算）
 * * 按照权重设置Map
 * 	* key： 渠道名称
 * 	* value： 权重
 * 思想： 取value权重的值，值对应的渠道在集合里添加对应的权重的数量
 * 如：
 * MAP： {"ali":2,"baidu":1,"tencent":"2"}
 * List: ["ali","ali","baidu","tencent","tencent"]
 * 在按照轮询/随机算法在获取对应集合下标的值即可
 *
 * 优化1： synchronized大量同步锁，性能不高，使用 AtomicLong对象解决线程安全问题
 */
@Component
public class RoundRobinSend extends BaseSendLoadBalancer {

    private static Integer pos = 0;

    @Override
    public String chooseChannel(List<SmsTemplate> smsTemplates, Set<String> mobile) {
        //获得当前模板对应的渠道 ALIYUN_SMS 2
        Map<String, String> channelMap = super.getChannelList(smsTemplates);

        // 取得通道地址List 3
        Set<String> keySet = channelMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String channelName = null;
        synchronized (pos) {
            if (pos >= keySet.size())
                pos = 0;
            channelName = keyList.get(pos);
            pos ++;
        }
        return channelName;
    }

}
