package com.itheima.restkeeper.handler.baidu;

import com.alibaba.fastjson.JSONObject;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.v3.*;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsTemplateEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsTemplateHandler;
import com.itheima.restkeeper.handler.baidu.config.BaiduSmsConfig;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.SmsTemplateVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @ClassName BaiduTemplateHandlerImpl.java
 * @Description 百度模板审核
 */
@Slf4j
@Service("baiduTemplateHandler")
public class BaiduTemplateHandlerImpl implements SmsTemplateHandler {

    @Autowired
    BaiduSmsConfig baiduSmsConfig;

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
            GetTemplateResponse response = query(smsTemplateVo);
            //处理返回结果
            String status = response.getStatus();
            //审核通过
            if ("READY".equals(status)) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                smsTemplateVo.setAuditMsg("审核通过");
                //审核失败
            } else if ("REJECTED".equals(status) || "ABORTED".equals(status)) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                smsTemplateVo.setAuditMsg(response.getReview());
            } else {
                log.info("百度云模板：{},审核中", response.getTemplateId());
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
                smsTemplateVo.setAuditMsg(response.getReview());
            }
            SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
            boolean flag = smsTemplateService.saveOrUpdate(smsTemplate);
            if (flag){
                return smsTemplate;
            }
            throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
        }
        CreateTemplateRequest request = new CreateTemplateRequest()
            //模板名称
            .withName(smsTemplateVo.getTemplateName())
            //模板内容
            .withContent(smsTemplateVo.getContent())
            //短信类型
            .withSmsType(smsTemplateVo.getSmsType())
            //适用国家类型
            //DOMESTIC：国内
            //INTERNATIONAL：国际/港澳台
            //GLOBAL：全球
            .withCountryType(smsTemplateVo.getInternational())
            //模板描述
            .withDescription(smsTemplateVo.getRemark());
        SmsClient client = baiduSmsConfig.queryClient();
        CreateTemplateResponse response = client.createTemplate(request);
        String status = response.getStatus();
        if ("SUBMITTED".equals(status)) {
            //受理成功
            smsTemplateVo.setAcceptStatus(SuperConstant.YES);
            smsTemplateVo.setAcceptMsg("受理成功");
            //审核中
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsTemplateVo.setAuditMsg("审核中");
            smsTemplateVo.setTemplateCode(response.getTemplateId());

        } else {
            smsTemplateVo.setAcceptStatus(SuperConstant.NO);
            smsTemplateVo.setAcceptMsg(response.getStatus());
        }
        //本地持久化
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
        boolean flag = smsTemplateService.save(smsTemplate);
        if (flag){
            return smsTemplate;
        }
        return null;
    }

    @Override
    public Boolean deleteSmsTemplate(SmsTemplateVo smsTemplateVo) {
        DeleteTemplateRequest request =new DeleteTemplateRequest()
            .withTemplateId(smsTemplateVo.getTemplateCode());
        SmsClient client = baiduSmsConfig.queryClient();
        client.deleteTemplate(request);
        return smsTemplateService.removeById(smsTemplateVo.getId());
    }

    @Override
    public Boolean modifySmsTemplate(SmsTemplateVo smsTemplateVo) {
        ModifyTemplateRequest request = new ModifyTemplateRequest()
            //模板id
            .withTemplateId(smsTemplateVo.getTemplateCode())
            //模板名称
            .withName(smsTemplateVo.getTemplateName())
            //模板内容
            .withContent(smsTemplateVo.getContent())
            //短信类型
            .withSmsType(smsTemplateVo.getSmsType())
            //适用国家类型
            //DOMESTIC：国内
            //INTERNATIONAL：国际/港澳台
            //GLOBAL：全球
            .withCountryType(smsTemplateVo.getInternational())
            //模板描述
            .withDescription(smsTemplateVo.getRemark());
        SmsClient client = baiduSmsConfig.queryClient();
        client.modifyTemplate(request);
        //受理成功
        smsTemplateVo.setAcceptStatus(SuperConstant.YES);
        smsTemplateVo.setAcceptMsg("受理成功");
        //审核中
        smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
        smsTemplateVo.setAuditMsg("审核中");
        //本地持久化
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
        return smsTemplateService.updateById(smsTemplate);
    }

    private GetTemplateResponse query(SmsTemplateVo smsTemplateVo){
        GetTemplateRequest request = new GetTemplateRequest()
                .withTemplateId(smsTemplateVo.getTemplateCode());
        SmsClient client = baiduSmsConfig.queryClient();
        return client.getTemplate(request);
    }

    @Override
    public Boolean querySmsTemplate(SmsTemplateVo smsTemplateVo) {
        GetTemplateResponse response = query(smsTemplateVo);
        //处理返回结果
        String status = response.getStatus();
        //审核通过
        if ("READY".equals(status)) {
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
            smsTemplateVo.setAuditMsg("审核通过");
            return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
            //审核失败
        } else if ("REJECTED".equals(status) || "ABORTED".equals(status)) {
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
            smsTemplateVo.setAuditMsg(response.getReview());
            return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
        } else {
            return true;
        }
    }
}
