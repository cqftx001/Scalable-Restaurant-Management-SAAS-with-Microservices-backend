package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.mapper.SmsTemplateMapper;
import com.itheima.restkeeper.req.SmsTemplateVo;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：模板表 服务实现类
 */
@Service
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements ISmsTemplateService {

    @Override
    public List<SmsTemplate> findSmsTemplateByTemplateNo(String templateNo) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getTemplateNo,templateNo)
                .eq(SmsTemplate::getEnableFlag, SuperConstant.YES)
                .eq(SmsTemplate::getAuditStatus,SuperConstant.STATUS_PASS_AUDIT);
        return list(queryWrapper);
    }

    @Override
    public Page<SmsTemplate> findSmsTemplateVoPage(SmsTemplateVo smsTemplateVo, int pageNum, int pageSize) {
        //构建分页对象
        Page<SmsTemplate> page = new Page<>(pageNum,pageSize);
        //构建查询条件
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        //按模板名称查询
        if (!EmptyUtil.isNullOrEmpty(smsTemplateVo.getTemplateName())) {
            queryWrapper.lambda().likeRight(SmsTemplate::getTemplateName,smsTemplateVo.getTemplateName());
        }
        //按模板名称查询
        if (!EmptyUtil.isNullOrEmpty(smsTemplateVo.getChannelLabel())) {
            queryWrapper.lambda().eq(SmsTemplate::getChannelLabel,smsTemplateVo.getChannelLabel());
        }
        //按模板发送状态查询
        if (!EmptyUtil.isNullOrEmpty(smsTemplateVo.getAcceptStatus())) {
            queryWrapper.lambda().eq(SmsTemplate::getAcceptStatus,smsTemplateVo.getAcceptStatus());
        }
        //按模板审核状态查询
        if (!EmptyUtil.isNullOrEmpty(smsTemplateVo.getAuditStatus())) {
            queryWrapper.lambda().eq(SmsTemplate::getAuditStatus,smsTemplateVo.getAuditStatus());
        }
        //按模板状态查询
        if (!EmptyUtil.isNullOrEmpty(smsTemplateVo.getEnableFlag())) {
            queryWrapper.lambda().eq(SmsTemplate::getEnableFlag,smsTemplateVo.getEnableFlag());
        }
        //按创建时间降序
        queryWrapper.lambda().orderByDesc(SmsTemplate::getCreatedTime);
        //执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public SmsTemplate findSmsTemplateByTemplateNameAndChannelLabel(String templateName, String channelLabel) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getTemplateName,templateName)
                .eq(SmsTemplate::getChannelLabel, channelLabel);
        return getOne(queryWrapper);
    }
}
