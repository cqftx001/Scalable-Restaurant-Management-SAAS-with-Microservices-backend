package com.itheima.restkeeper.handler.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.enums.SmsTemplateEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsTemplateHandler;
import com.itheima.restkeeper.handler.aliyun.config.AliyunSmsConfig;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.SmsTemplateVo;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SmsTemplateAdapter.java
 * @Description 阿里云模板处理器接口
 */
@Slf4j
@Service("aliyunTemplateHandler")
public class AliyunTemplateHandlerImpl implements SmsTemplateHandler {

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Override
    public SmsTemplate addSmsTemplate(SmsTemplateVo smsTemplateVo) {
        //查询是否添加过模板
        SmsTemplate smsTemplateHandler = smsTemplateService.findSmsTemplateByTemplateNameAndChannelLabel(
                smsTemplateVo.getTemplateName(),
                smsTemplateVo.getChannelLabel());
        if (!EmptyUtil.isNullOrEmpty(smsTemplateHandler)){
            smsTemplateVo = BeanConv.toBean(smsTemplateHandler,SmsTemplateVo.class);
            QuerySmsTemplateResponse response = query(smsTemplateVo);
            //受理状态
            String code = response.getBody().getCode();
            String message = response.getBody().getMessage();
            if ("OK".equals(code)){
                Integer templateStatus =response.getBody().getTemplateStatus();
                smsTemplateVo.setTemplateCode(response.getBody().getTemplateCode());
                //审核通过
                if (templateStatus == 1) {
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                    smsTemplateVo.setAuditMsg("审核通过");
                //审核失败
                } else if (templateStatus == 2) {
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                    smsTemplateVo.setAuditMsg(response.getBody().getReason());
                } else {
                    log.info("阿里云模板：{},审核中", response.getBody().getTemplateCode());
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
                    smsTemplateVo.setAuditMsg(response.getBody().getReason());
                }
                SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
                boolean flag = smsTemplateService.saveOrUpdate(smsTemplate);
                if (flag){
                    return smsTemplate;
                }
                throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
            }
        }
        AddSmsTemplateRequest request = new AddSmsTemplateRequest();
        //短信类型。取值：
        //0：验证码。
        //1：短信通知。
        //2：推广短信。
        //3：国际/港澳台消息。
        request.setTemplateType(Integer.valueOf(smsTemplateVo.getSmsType()));
        request.setTemplateName(smsTemplateVo.getTemplateName());
        request.setTemplateContent(smsTemplateVo.getContent());
        request.setRemark(smsTemplateVo.getRemark());
        Client client =aliyunSmsConfig.queryClient();
        AddSmsTemplateResponse response = null;
        try {
            response = client.addSmsTemplate(request);
        } catch (Exception e) {
            log.error("请求添加阿里云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
        }
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            //受理成功
            smsTemplateVo.setAcceptStatus(SuperConstant.YES);
            smsTemplateVo.setAcceptMsg("受理成功");
            //审核中
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsTemplateVo.setAuditMsg("审核中");
            //短信模板CODE。
            //您可以使用模板CODE通过
            //QuerySmsTemplate接口或短信服务控制台查看模板申请状态和结果。
            smsTemplateVo.setTemplateCode(response.getBody().getTemplateCode());
        }else {
            //受理失败
            smsTemplateVo.setAcceptStatus(SuperConstant.NO);
            smsTemplateVo.setAcceptMsg(message);
        }
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
        boolean flag = smsTemplateService.save(smsTemplate);
        if (flag){
            return smsTemplate;
        }
        throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
    }

    @Override
    public Boolean deleteSmsTemplate(SmsTemplateVo smsTemplateVo) {
        DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
        request.setTemplateCode(smsTemplateVo.getTemplateCode());
        Client client =aliyunSmsConfig.queryClient();
        DeleteSmsTemplateResponse response = null;
        try {
            response = client.deleteSmsTemplate(request);
        } catch (Exception e) {
            log.error("请求删除阿里云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.DELETE_FAIL);
        }
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        return smsTemplateService.removeById(smsTemplateVo.getId());
    }

    @Override
    public Boolean modifySmsTemplate(SmsTemplateVo smsTemplateVo) {
        ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
        //短信类型。取值：
        //0：验证码。
        //1：短信通知。
        //2：推广短信。
        //3：国际/港澳台消息。
        request.setTemplateType(Integer.valueOf(smsTemplateVo.getSmsType()));
        request.setTemplateName(smsTemplateVo.getTemplateName());
        request.setTemplateCode(smsTemplateVo.getTemplateCode());
        request.setTemplateContent(smsTemplateVo.getContent());
        request.setRemark(smsTemplateVo.getRemark());
        Client client =aliyunSmsConfig.queryClient();
        ModifySmsTemplateResponse response = null;
        try {
            response = client.modifySmsTemplate(request);
        } catch (Exception e) {
            log.error("请求修改阿里云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.UPDATE_FAIL);
        }
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            //受理成功
            smsTemplateVo.setAcceptStatus(SuperConstant.YES);
            smsTemplateVo.setAcceptMsg("受理成功");
            //审核中
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsTemplateVo.setAuditMsg("审核中");
            //短信模板CODE。
            //您可以使用模板CODE通过
            //QuerySmsTemplate接口或短信服务控制台查看模板申请状态和结果。
            smsTemplateVo.setTemplateCode(response.getBody().getTemplateCode());
        }else {
            //受理失败
            smsTemplateVo.setAcceptStatus(SuperConstant.NO);
            smsTemplateVo.setAcceptMsg( message);
            smsTemplateVo.setAuditStatus(null);
            smsTemplateVo.setAuditMsg(null);
            smsTemplateVo.setTemplateCode(null);
        }
        //本地持久化
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
        return smsTemplateService.updateById(smsTemplate);
    }

    private QuerySmsTemplateResponse query(SmsTemplateVo smsTemplateVo) {
        QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
        request.setTemplateCode(smsTemplateVo.getTemplateCode());
        Client client =aliyunSmsConfig.queryClient();
        QuerySmsTemplateResponse response = null;
        try {
            response = client.querySmsTemplate(request);
        } catch (Exception e) {
            log.error("请求查询阿里云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
        return response;
    }

    @Override
    public Boolean querySmsTemplate(SmsTemplateVo smsTemplateVo) {
        QuerySmsTemplateResponse response =query(smsTemplateVo);
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            Integer templateStatus =response.getBody().getTemplateStatus();
            //审核通过
            if (templateStatus == 1) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                smsTemplateVo.setAuditMsg("审核通过");
                return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
            //审核失败
            } else if (templateStatus == 2) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                smsTemplateVo.setAuditMsg(response.getBody().getReason());
                return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
            } else {
                log.info("阿里云模板：{},审核中", response.getBody().getTemplateCode());
                return true;
            }
        }else {
            log.warn("受理查询阿里云模板出错：{}", message);
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
    }
}
