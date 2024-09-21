package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.basic.BasicPojo;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.mapper.SmsSignMapper;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description： 签名服务实现类
 */
@Service
public class SmsSignServiceImpl extends ServiceImpl<SmsSignMapper, SmsSign> implements ISmsSignService {

    @Override
    public Page<SmsSign> findSmsSignVoPage(SmsSignVo smsSignVo, int pageNum, int pageSize) {
        //构建分页对象
        Page<SmsSign> page = new Page<>(pageNum,pageSize);
        //构建查询条件
        QueryWrapper<SmsSign> queryWrapper = new QueryWrapper<>();
        //按签名名称查询
        if (!EmptyUtil.isNullOrEmpty(smsSignVo.getSignName())) {
            queryWrapper.lambda().likeRight(SmsSign::getSignName,smsSignVo.getSignName());
        }
        //按签名通道查询
        if (!EmptyUtil.isNullOrEmpty(smsSignVo.getChannelLabel())) {
            queryWrapper.lambda().eq(SmsSign::getChannelLabel,smsSignVo.getChannelLabel());
        }
        //按签名发送状态查询
        if (!EmptyUtil.isNullOrEmpty(smsSignVo.getAcceptStatus())) {
            queryWrapper.lambda().eq(SmsSign::getAcceptStatus,smsSignVo.getAcceptStatus());
        }
        //按签名审核状态查询
        if (!EmptyUtil.isNullOrEmpty(smsSignVo.getAuditStatus())) {
            queryWrapper.lambda().eq(SmsSign::getAuditStatus,smsSignVo.getAuditStatus());
        }
        //按签名状态查询
        if (!EmptyUtil.isNullOrEmpty(smsSignVo.getEnableFlag())) {
            queryWrapper.lambda().eq(SmsSign::getEnableFlag,smsSignVo.getEnableFlag());
        }
        //按创建时间降序
        queryWrapper.lambda().orderByDesc(SmsSign::getCreatedTime);
        //执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public List<SmsSign> findSmsSignVoList() {
        QueryWrapper<SmsSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BasicPojo::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }

    @Override
    public SmsSign findSmsSignBySignNameAndChannelLabel(String signName, String channelLabel) {
        QueryWrapper<SmsSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsSign::getSignName, signName)
                .eq(SmsSign::getChannelLabel, channelLabel);
        return getOne(queryWrapper);
    }

    @Override
    public SmsSign findSmsSignBySignNoAndChannelLabel(String signNo, String channelLabel) {
        QueryWrapper<SmsSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsSign::getSignNo, signNo)
                .eq(SmsSign::getChannelLabel, channelLabel)
                .eq(SmsSign::getAuditStatus,SuperConstant.STATUS_PASS_AUDIT);
        return getOne(queryWrapper);
    }

    @Override
    public SmsSign findSmsSignBySignCodeAndChannelLabel(String signCode, String channelLabel) {
        QueryWrapper<SmsSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsSign::getSignCode, signCode)
                .eq(SmsSign::getChannelLabel, channelLabel)
                .eq(SmsSign::getAuditStatus,SuperConstant.STATUS_PASS_AUDIT);
        return getOne(queryWrapper);
    }
}
