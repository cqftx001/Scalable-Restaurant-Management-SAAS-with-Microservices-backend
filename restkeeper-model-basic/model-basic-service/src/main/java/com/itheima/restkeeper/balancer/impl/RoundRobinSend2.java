package com.itheima.restkeeper.balancer.impl;

import com.itheima.restkeeper.balancer.BaseSendLoadBalancer;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
 * 存在的问题： AtomicInteger 基于CAS实现占用CPU， 使用线程调度解决
 *
 * 优化2：参考SpringCloud Ribbon 负载均衡算法，优化当前代码
 */
@Component("roundRobinSend2")
@Slf4j
public class RoundRobinSend2 extends BaseSendLoadBalancer {

    private AtomicInteger nextChannelCyclicCounter;

    public RoundRobinSend2() {
        nextChannelCyclicCounter = new AtomicInteger(0);
    }


    public String chooseChannel(List<SmsTemplate> smsTemplates, Set<String> mobile) {
        if (EmptyUtil.isNullOrEmpty(smsTemplates)) {
            log.warn("no load balancer");
            return null;
        }
        //获取所有的渠道列表
        Map<String, String> channelMap = super.getChannelList(smsTemplates);
        Set<String> keySet = channelMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);


        String channelName = null;
        int count = 0;
        //循环10次后自动跳出循环
        while (channelName == null && count++ < 10) {

            // 得到所有的渠道集合大小
            int upCount = keyList.size();

            if (upCount == 0) {
                log.warn("No up servers available from load balancer: " + smsTemplates);
                return null;
            }

            //轮询算法实现
            int nextServerIndex = incrementAndGetModulo(upCount);
            channelName = keyList.get(nextServerIndex);

            if (channelName == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }
        }
        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: ");
        }
        return channelName;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     * @param modulo 集合大小
     * @return 获取集合中下标
     */
    private int incrementAndGetModulo(int modulo) {
        for (;;) {  //自旋锁
            int current = nextChannelCyclicCounter.get();
            int next = (current + 1) % modulo;  // 避免数组越界
            if (nextChannelCyclicCounter.compareAndSet(current, next))  // CAS 思想
                return next;
        }
    }

}
