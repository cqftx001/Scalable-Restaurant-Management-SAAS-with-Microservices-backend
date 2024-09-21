package com.itheima.restkeeper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.basic.BasicPojo;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsChannelEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.aliyun.config.AliyunSmsConfig;
import com.itheima.restkeeper.handler.baidu.config.BaiduSmsConfig;
import com.itheima.restkeeper.handler.tencent.config.TencentSmsConfig;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.mapper.SmsChannelMapper;
import com.itheima.restkeeper.req.SmsChannelVo;
import com.itheima.restkeeper.service.ISmsChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.apache.http.impl.nio.reactor.ChannelEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Description： 服务实现类
 */
@Service
public class SmsChannelServiceImpl extends ServiceImpl<SmsChannelMapper, SmsChannel> implements ISmsChannelService {

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    @Autowired
    TencentSmsConfig tencentSmsConfig;

    @Autowired
    BaiduSmsConfig baiduSmsConfig;

    @Override
    public SmsChannel findChannelByChannelLabel(String channelLabel) {
        QueryWrapper<SmsChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SmsChannel::getEnableFlag, SuperConstant.YES)
                .eq(SmsChannel::getChannelLabel,channelLabel);
        return getOne(queryWrapper);
    }

    @Override
    public List<SmsChannel> findChannelInChannelLabel(Set<String> channelLabels) {
        QueryWrapper<SmsChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SmsChannel::getEnableFlag, SuperConstant.YES)
                .in(SmsChannel::getChannelLabel,channelLabels);
        return list(queryWrapper);
    }

    @Override
    public Page<SmsChannel> findSmsChannelVoPage(SmsChannelVo smsChannelVo, int pageNum, int pageSize) {
        Page<SmsChannel> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SmsChannel> queryWrapper = new QueryWrapper<>();

        if (!EmptyUtil.isNullOrEmpty(smsChannelVo.getChannelLabel())) {
            queryWrapper.lambda().eq(SmsChannel::getChannelLabel,smsChannelVo.getChannelLabel());
        }
        if (!EmptyUtil.isNullOrEmpty(smsChannelVo.getChannelName())) {
            queryWrapper.lambda().likeRight(SmsChannel::getChannelName,smsChannelVo.getChannelName());
        }
        if (!EmptyUtil.isNullOrEmpty(smsChannelVo.getEnableFlag())) {
            queryWrapper.lambda().eq(SmsChannel::getEnableFlag,smsChannelVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByAsc(SmsChannel::getCreatedTime);
        return page(page, queryWrapper);
    }

    @Override
    public SmsChannel createSmsChannel(SmsChannelVo smsChannelVo) {
        SmsChannel smsChannel = BeanConv.toBean(smsChannelVo, SmsChannel.class);
        smsChannel.setOtherConfig(JSONObject.toJSONString(smsChannelVo.getOtherConfigs()));
        boolean flag = save(smsChannel);
        if (flag){
            if (SuperConstant.ALIYUN_SMS.equals(smsChannelVo.getChannelLabel())){
                aliyunSmsConfig.createOrUpdateClient(smsChannel);
            }else if (SuperConstant.BAIDU_SMS.equals(smsChannelVo.getChannelLabel())){
                baiduSmsConfig.createOrUpdateClient(smsChannel);
            }else if (SuperConstant.TENCENT_SMS.equals(smsChannelVo.getChannelLabel())){
                tencentSmsConfig.createOrUpdateClient(smsChannel);
            }else {
                throw new ProjectException(SmsChannelEnum.FAIL);
            }
            return smsChannel;
        }
        return null;
    }

    @Override
    public Boolean updateSmsChannel(SmsChannelVo smsChannelVo) {
        SmsChannel smsChannel = BeanConv.toBean(smsChannelVo, SmsChannel.class);
        smsChannel.setOtherConfig(JSONObject.toJSONString(smsChannelVo.getOtherConfigs()));
        boolean flag = updateById(smsChannel);
        if (flag){
            if (SuperConstant.ALIYUN_SMS.equals(smsChannelVo.getChannelLabel())){
                aliyunSmsConfig.createOrUpdateClient(smsChannel);
            }else if (SuperConstant.BAIDU_SMS.equals(smsChannelVo.getChannelLabel())){
                baiduSmsConfig.createOrUpdateClient(smsChannel);
            }else if (SuperConstant.TENCENT_SMS.equals(smsChannelVo.getChannelLabel())){
                tencentSmsConfig.createOrUpdateClient(smsChannel);
            }else {
                throw new ProjectException(SmsChannelEnum.FAIL);
            }
            return flag;
        }
        return flag;
    }

    @Override
    public Boolean deleteSmsChannel(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        for (String id : ids) {
            SmsChannel smsChannel = getById(id);
            if (SuperConstant.ALIYUN_SMS.equals(smsChannel.getChannelLabel())){
                aliyunSmsConfig.removeClient();
            }else if (SuperConstant.BAIDU_SMS.equals(smsChannel.getChannelLabel())){
                baiduSmsConfig.removeClient();
            }else if (SuperConstant.TENCENT_SMS.equals(smsChannel.getChannelLabel())){
                tencentSmsConfig.removeClient();
            }else {
                throw new ProjectException(SmsChannelEnum.FAIL);
            }
            removeById(id);
        }
        return true;
    }

    @Override
    public List<SmsChannel> findSmsChannelVoList() {
        QueryWrapper<SmsChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BasicPojo::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }

}
