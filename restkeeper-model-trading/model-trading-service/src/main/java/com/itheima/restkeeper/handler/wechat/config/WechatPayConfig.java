package com.itheima.restkeeper.handler.wechat.config;

import com.alibaba.fastjson.JSONArray;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.handler.wechat.client.WechatPayClient;
import com.itheima.restkeeper.pojo.PayChannel;
import com.itheima.restkeeper.req.OtherConfigVo;
import com.itheima.restkeeper.req.PayChannelVo;
import com.itheima.restkeeper.service.IPayChannelService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WeChatPayConfig.java
 * @Description 微信配置类
 */
@Configuration
public class WechatPayConfig {

    @Autowired
    IPayChannelService payChannelService;

    @Autowired
    RedissonClient redissonClient;


    /***
     * @description 支付配置文件初始化
     */
    @PostConstruct
    public void InitWechatPayConfig() {
        //1、查询所有商户支付配置列表
        List<PayChannel> payChannels = payChannelService.findPayChannelList(TradingConstant.TRADING_CHANNEL_WECHAT_PAY);
        List<PayChannelVo> payChannelVos = BeanConv.toBeanList(payChannels,PayChannelVo.class);
        if (!EmptyUtil.isNullOrEmpty(payChannelVos)){
            payChannelVos.forEach(n->{
                List <OtherConfigVo> otherConfigVos = JSONArray.parseArray(n.getOtherConfig(),OtherConfigVo.class);
                n.setOtherConfigs(otherConfigVos);
                RBucket<PayChannelVo> wechatPayChannel = redissonClient.getBucket("pay:wechat:channel:"+n.getEnterpriseId());
                wechatPayChannel.set(n);
            });
        }
    }

    /***
     * @description 初始化或更新配置进入redis
     * @param payChannelVo 配置信息
     * @return
     */
    public WechatPayClient createOrUpdateConfig(PayChannelVo payChannelVo) {
        //1、缓存配置
        RBucket<PayChannelVo> wechatPayChannel = redissonClient
                .getBucket("pay:wechat:channel:"+payChannelVo.getEnterpriseId());
        wechatPayChannel.set(payChannelVo);
        List<OtherConfigVo> otherConfigs = payChannelVo.getOtherConfigs();
        //2、转换其他属性为map结构
        Map<String,String> otherConfigMap = new HashMap<>();
        if (!EmptyUtil.isNullOrEmpty(otherConfigs)) {
            otherConfigs.forEach(n->{
                otherConfigMap.put(n.getConfigKey(),n.getConfigValue());
            });
        }

        return WechatPayClient.builder()
            .appid(payChannelVo.getAppId())
            .domain(payChannelVo.getDomain())
            .mchId(otherConfigMap.get("mchId"))
            .mchSerialNo(otherConfigMap.get("mchSerialNo"))
            .apiV3Key(otherConfigMap.get("apiV3Key"))
            .privateKey(payChannelVo.getMerchantPrivateKey())
            .build();
    }

    /***
     * @description 移除配置
     * @return
     */
    public void removeConfig(Long enterpriseId){
        //1、缓存配置
        RBucket<PayChannelVo> wechatPayChannel = redissonClient
                .getBucket("pay:wechat:channel:"+enterpriseId);
        wechatPayChannel.delete();
    }

    /***
     * @description 获得配置
     * @return
     */
    public WechatPayClient queryConfig(Long enterpriseId){
        //1、缓存配置
        RBucket<PayChannelVo> wechatPayChannel = redissonClient
                .getBucket("pay:wechat:channel:"+enterpriseId);
        return this.createOrUpdateConfig(wechatPayChannel.get());
    }
}
