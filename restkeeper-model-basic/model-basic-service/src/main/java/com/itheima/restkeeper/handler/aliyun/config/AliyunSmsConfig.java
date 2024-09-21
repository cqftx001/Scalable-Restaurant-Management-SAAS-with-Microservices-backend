package com.itheima.restkeeper.handler.aliyun.config;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.RegisterBeanHandler;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName AlipayConfig.java
 * @Description 支付宝配置类
 */
@Slf4j
@Configuration
public class AliyunSmsConfig {

    @Autowired
    ISmsChannelService smsChannelService;

    @Autowired
    RedissonClient redissonClient;

    /***
     * @description 初始化短信配置
     */
    @PostConstruct
    public void initSmsConfig() {
        //查询阿里云SMS的配置信息
        SmsChannel smsChannel = smsChannelService.findChannelByChannelLabel(SuperConstant.ALIYUN_SMS);
        if (EmptyUtil.isNullOrEmpty(smsChannel)){
            log.warn("阿里云SMS的未配置");
            return;
        }
        RBucket<SmsChannel> aliyunSmsClient = redissonClient.getBucket("sms:aliyunSmsChannel");
        aliyunSmsClient.set(smsChannel);
    }

    public Client createOrUpdateClient(SmsChannel smsChannel){
        RBucket<SmsChannel> aliyunSmsClient = redissonClient.getBucket("sms:aliyunSmsChannel");
        aliyunSmsClient.set(smsChannel);
        Config config = new Config()
            // 阿里云AccessKey ID
            .setAccessKeyId(smsChannel.getAccessKeyId())
            // 阿里云AccessKey Secret
            .setAccessKeySecret(smsChannel.getAccessKeySecret());
        // 访问的域名
        config.endpoint = smsChannel.getDomain();
        try {
            return new Client(config);
        } catch (Exception e) {
            log.error("阿里云SMS的配置信息出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            return null;
        }

    }

    /***
     * @description 移除配置
     * @return
     */
    public void removeClient(){
        RBucket<SmsChannel> aliyunSmsClient = redissonClient.getBucket("sms:aliyunSmsChannel");
        aliyunSmsClient.delete();
    }

    /***
     * @description 获得配置
     * @return
     */
    public Client queryClient(){
        RBucket<SmsChannel> aliyunSmsClient = redissonClient.getBucket("sms:aliyunSmsChannel");
        return this.createOrUpdateClient(aliyunSmsClient.get());
    }
}
