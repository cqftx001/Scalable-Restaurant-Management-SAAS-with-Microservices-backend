package com.itheima.restkeeper.adapter.impl;

import com.itheima.restkeeper.adapter.SmsSignAdapter;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.handler.SmsSignHandler;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.RegisterBeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SmsSignAdapterImpl.java
 * @Description 签名适配器实现
 */
@Service("smsSignAdapter")
public class SmsSignAdapterImpl implements SmsSignAdapter {

    @Autowired
    SmsSignHandler aliyunSmsSignHandler;

    @Autowired
    SmsSignHandler tencentSmsSignHandler;

    @Autowired
    SmsSignHandler baiduSmsSignHandler;

    @Autowired
    ISmsSignService smsSignService;

    @Autowired
    RegisterBeanHandler registerBeanHandler;

    private static Map<String,String> smsSignHandlers =new HashMap<>();

    static {
        smsSignHandlers.put(SuperConstant.ALIYUN_SMS,"aliyunSmsSignHandler");
        smsSignHandlers.put(SuperConstant.TENCENT_SMS,"tencentSmsSignHandler");
        smsSignHandlers.put(SuperConstant.BAIDU_SMS,"baiduSmsSignHandler");
    }

    @Override
    public SmsSignVo addSmsSign(SmsSignVo smsSignVo) {
        String channelLabel = smsSignVo.getChannelLabel();
        String stringSmsSignHandler = smsSignHandlers.get(channelLabel);
        SmsSignHandler smsSignHandler =registerBeanHandler.getBean(stringSmsSignHandler,SmsSignHandler.class);
        return BeanConv.toBean(smsSignHandler.addSmsSign(smsSignVo),SmsSignVo.class);
    }

    @Override
    @Transactional
    public Boolean deleteSmsSign(String[] checkedIds) {
        for (String checkedId : checkedIds) {
            SmsSignVo smsSignVo = BeanConv.toBean(smsSignService.getById(checkedId), SmsSignVo.class);
            String channelLabel = smsSignVo.getChannelLabel();
            String stringSmsSignHandler = smsSignHandlers.get(channelLabel);
            SmsSignHandler smsSignHandler =registerBeanHandler.getBean(stringSmsSignHandler,SmsSignHandler.class);
            smsSignHandler.deleteSmsSign(smsSignVo);
        }
        return true;
    }

    @Override
    public Boolean modifySmsSign(SmsSignVo smsSignVo) {
        String channelLabel = smsSignVo.getChannelLabel();
        String stringSmsSignHandler = smsSignHandlers.get(channelLabel);
        SmsSignHandler smsSignHandler =registerBeanHandler.getBean(stringSmsSignHandler,SmsSignHandler.class);
        return smsSignHandler.modifySmsSign(smsSignVo);
    }

    @Override
    public Boolean querySmsSign(SmsSignVo smsSignVo) {
        String channelLabel = smsSignVo.getChannelLabel();
        String stringSmsSignHandler = smsSignHandlers.get(channelLabel);
        SmsSignHandler smsSignHandler =registerBeanHandler.getBean(stringSmsSignHandler,SmsSignHandler.class);
        return smsSignHandler.querySmsSign(smsSignVo);
    }
}
