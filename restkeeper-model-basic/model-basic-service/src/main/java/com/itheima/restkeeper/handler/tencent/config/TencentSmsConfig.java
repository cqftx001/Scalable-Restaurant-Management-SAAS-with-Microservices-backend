package com.itheima.restkeeper.handler.tencent.config;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @ClassName AlipayConfig.java
 * @Description 支付宝配置类
 */
@Slf4j
@Configuration
public class TencentSmsConfig {

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
        SmsChannel smsChannel = smsChannelService.findChannelByChannelLabel(SuperConstant.TENCENT_SMS);
        if (EmptyUtil.isNullOrEmpty(smsChannel)){
            log.warn("腾讯云SMS的未配置");
            return;
        }
        RBucket<SmsChannel> tencentSmsChannel = redissonClient.getBucket("sms:tencentSmsChannel");
        tencentSmsChannel.set(smsChannel);
    }

    public SmsClient createOrUpdateClient(SmsChannel smsChannel){
        RBucket<SmsChannel> tencentSmsChannel = redissonClient.getBucket("sms:tencentSmsChannel");
        tencentSmsChannel.set(smsChannel);
        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
         * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
         * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
         * 以免泄露密钥对危及你的财产安全。
         * CAM密匙查询: https://console.cloud.tencent.com/cam/capi*/
        Credential cred = new Credential(smsChannel.getAccessKeyId(), smsChannel.getAccessKeySecret());
        // 实例化一个http选项，可选，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        // 设置代理
        // httpProfile.setProxyHost("真实代理ip");
        // httpProfile.setProxyPort(真实代理端口);
        /* SDK默认使用POST方法。
         * 如果你一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
        httpProfile.setReqMethod("POST");
        /* SDK有默认的超时时间，非必要请不要进行调整
         * 如有需要请在代码中查阅以获取最新的默认值 */
        httpProfile.setConnTimeout(60);
        /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
         * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
        httpProfile.setEndpoint(smsChannel.getDomain());
        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* SDK默认用TC3-HMAC-SHA256进行签名
         * 非必要请不要修改这个字段 */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
        return new SmsClient(cred, "ap-guangzhou", clientProfile);
    }

    /***
     * @description 移除配置
     * @return
     */
    public void removeClient(){
        RBucket<SmsChannel> tencentSmsChannel = redissonClient.getBucket("sms:tencentSmsChannel");
        tencentSmsChannel.delete();
    }

    /***
     * @description 获得配置
     * @return
     */
    public SmsClient queryClient(){
        RBucket<SmsChannel> tencentSmsChannel = redissonClient.getBucket("sms:tencentSmsChannel");
        return this.createOrUpdateClient(tencentSmsChannel.get());
    }
}
