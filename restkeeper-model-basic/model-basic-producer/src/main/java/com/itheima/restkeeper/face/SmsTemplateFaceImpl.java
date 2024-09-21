package com.itheima.restkeeper.face;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsTemplateFace;
import com.itheima.restkeeper.adapter.SmsTemplateAdapter;
import com.itheima.restkeeper.enums.SmsTemplateEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.OtherConfigVo;
import com.itheima.restkeeper.req.SmsTemplateVo;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SmsTemplateFaceImpl.java
 * @Description TODO
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findSmsTemplateVoPage",retries = 2),
        @Method(name = "addSmsTemplate",retries = 0),
        @Method(name = "deleteSmsTemplate",retries = 0),
        @Method(name = "modifySmsTemplate",retries = 0),
        @Method(name = "querySmsTemplate",retries = 2)
    })
public class SmsTemplateFaceImpl implements SmsTemplateFace {

    @Autowired
    SmsTemplateAdapter smsTemplateAdapter;

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Override
    public Page<SmsTemplateVo> findSmsTemplateVoPage(SmsTemplateVo smsTemplateVo, int pageNum, int pageSize) {
        try {
            //查询Page<SmsTemplateVo>图片分页
            Page<SmsTemplate> page = smsTemplateService.findSmsTemplateVoPage(smsTemplateVo,pageNum,pageSize);
            //转化Page<SmsTemplateVo>为Page<SmsTemplateVo>
            Page<SmsTemplateVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //转换List<SmsTemplateVo>为 List<SmsTemplateVo>
            List<SmsTemplate> smsTemplateList = page.getRecords();
            List<SmsTemplateVo> smsTemplateVoList = new ArrayList<>();
            if (!EmptyUtil.isNullOrEmpty(smsTemplateList)){
                smsTemplateList.forEach(n->{
                    SmsTemplateVo smsTemplateVoHandler = BeanConv.toBean(n, SmsTemplateVo.class);
                    if (!EmptyUtil.isNullOrEmpty(n.getOtherConfig())){
                        List <OtherConfigVo> list = JSONArray.parseArray(n.getOtherConfig(), OtherConfigVo.class);
                        smsTemplateVoHandler.setOtherConfigs(list);
                    }
                    smsTemplateVoList.add(smsTemplateVoHandler);
                });
            }
            pageVo.setRecords(smsTemplateVoList);
            //返回结果
            return pageVo;
        } catch (Exception e) {
            log.error("查询签名列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.PAGE_FAIL);
        }
    }

    @Override
    public SmsTemplateVo addSmsTemplate(SmsTemplateVo smsTemplateVo) throws ProjectException {
        try {
            return smsTemplateAdapter.addSmsTemplate(smsTemplateVo);
        } catch (Exception e) {
            log.error("添加签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean deleteSmsTemplate(String[] checkedIds) throws ProjectException {
        try {
            return smsTemplateAdapter.deleteSmsTemplate(checkedIds);
        } catch (Exception e) {
            log.error("删除签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.DELETE_FAIL);
        }
    }

    @Override
    public Boolean modifySmsTemplate(SmsTemplateVo smsTemplateVo) throws ProjectException {
        try {
            return smsTemplateAdapter.modifySmsTemplate(smsTemplateVo);
        } catch (Exception e) {
            log.error("修改签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean querySmsTemplate(SmsTemplateVo smsTemplateVo) throws ProjectException {
        try {
            return smsTemplateAdapter.querySmsTemplate(smsTemplateVo);
        } catch (Exception e) {
            log.error("查询签名状态异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.SELECT_FAIL);
        }
    }

    @Override
    public Boolean updateSmsTemplateEnableFlag(SmsTemplateVo smsTemplateVo) {
        try {
            return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo,SmsTemplate.class));
        } catch (Exception e) {
            log.error("修改签名状态异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.UPDATE_FAIL);
        }
    }

}
