package com.itheima.restkeeper.handler.tencent;

import com.alibaba.fastjson.JSONObject;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.enums.SmsTemplateEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsTemplateHandler;
import com.itheima.restkeeper.handler.tencent.config.TencentSmsConfig;
import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.SmsTemplateVo;
import com.itheima.restkeeper.service.ISmsTemplateService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TencentTemplateHandlerImpl.java
 * @Description 腾讯云模板处理器接口
 */
@Slf4j
@Service("tencentTemplateHandler")
public class TencentTemplateHandlerImpl implements SmsTemplateHandler {

    @Autowired
    TencentSmsConfig tencentSmsConfig;

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
            DescribeSmsTemplateListResponse response = query(smsTemplateVo);
            //处理结果
            DescribeTemplateListStatus[] describeTemplateStatusSet = response.getDescribeTemplateStatusSet();
            response.getDescribeTemplateStatusSet();
            if (!EmptyUtil.isNullOrEmpty(describeTemplateStatusSet)){
                DescribeTemplateListStatus describeTemplateListStatus = describeTemplateStatusSet[0];
                Long statusCode = describeTemplateListStatus.getStatusCode();
                //审核通过
                if (statusCode == 0) {
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                    smsTemplateVo.setAuditMsg("审核通过");
                //审核失败
                } else if (statusCode == -1) {
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                    smsTemplateVo.setAuditMsg(describeTemplateListStatus.getReviewReply());
                } else {
                    log.info("腾讯云模板：{},审核中", describeTemplateListStatus.getTemplateId());
                    smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
                    smsTemplateVo.setAuditMsg(describeTemplateListStatus.getReviewReply());
                }
                SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
                smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
                boolean flag = smsTemplateService.saveOrUpdate(smsTemplate);
                if (flag){
                    return smsTemplate;
                }
                throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
            }
        }
        // 实例化一个请求对象,每个接口都会对应一个request对象
        AddSmsTemplateRequest request = new AddSmsTemplateRequest();
        //模板名称
        request.setTemplateName(smsTemplateVo.getTemplateName());
        //模板内容
        request.setTemplateContent(smsTemplateVo.getContent());
        //短信类型，0表示普通短信, 1表示营销短信。
        request.setSmsType(Long.valueOf(smsTemplateVo.getSmsType()));
        //是否国际/港澳台短信：
        //0：表示国内短信。
        //1：表示国际/港澳台短信。
        request.setInternational(Long.valueOf(smsTemplateVo.getInternational()));
        //模板备注，例如申请原因，使用场景等
        request.setRemark(smsTemplateVo.getRemark());
        // 返回的resp是一个AddSmsTemplateResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        AddSmsTemplateResponse response = null;
        try {
            response = smsClient.AddSmsTemplate(request);
        } catch (TencentCloudSDKException e) {
            log.error("请求添加腾讯云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
        }
        //受理状态
        String templateId = response.getAddTemplateStatus().getTemplateId();
        if (!EmptyUtil.isNullOrEmpty(templateId)) {
            //受理成功
            smsTemplateVo.setAcceptStatus(SuperConstant.YES);
            smsTemplateVo.setAcceptMsg("受理成功");
            //审核中
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsTemplateVo.setAuditMsg("审核中");
            //短信模板CODE。
            //您可以使用模板CODE通过
            smsTemplateVo.setTemplateCode(templateId);
            //QuerySmsTemplate接口或短信服务控制台查看模板申请状态和结果。
        } else {
            //受理失败
            smsTemplateVo.setAcceptStatus(SuperConstant.NO);
            smsTemplateVo.setAcceptMsg("受理失败");
        }
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        boolean flag = smsTemplateService.save(smsTemplate);
        if (flag){
            return smsTemplate;
        }
        throw new ProjectException(SmsTemplateEnum.CREATE_FAIL);
    }

    @Override
    public Boolean deleteSmsTemplate(SmsTemplateVo smsTemplateVo) {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
        request.setTemplateId(Long.valueOf(smsTemplateVo.getTemplateCode()));
        // 返回的resp是一个DeleteSmsTemplateResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        DeleteSmsTemplateResponse response = null;
        try {
            response = smsClient.DeleteSmsTemplate(request);
        } catch (TencentCloudSDKException e) {
            log.error("请求删除腾讯云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsTemplateEnum.DELETE_FAIL);
        }
        return smsTemplateService.removeById(smsTemplateVo.getId());
    }

    @Override
    public Boolean modifySmsTemplate(SmsTemplateVo smsTemplateVo) {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
        //模板名称
        request.setTemplateName(smsTemplateVo.getTemplateName());
        //模板code
        request.setTemplateId(Long.valueOf(smsTemplateVo.getTemplateCode()));
        //模板内容
        request.setTemplateContent(smsTemplateVo.getContent());
        //短信类型，0表示普通短信, 1表示营销短信。
        request.setSmsType(Long.valueOf(smsTemplateVo.getSmsType()));
        //是否国际/港澳台短信：
        //0：表示国内短信。
        //1：表示国际/港澳台短信。
        request.setInternational(Long.valueOf(smsTemplateVo.getInternational()));
        //模板备注，例如申请原因，使用场景等
        request.setRemark(smsTemplateVo.getRemark());
        // 返回的resp是一个ModifySmsTemplateResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        ModifySmsTemplateResponse response = null;
        try {
            response = smsClient.ModifySmsTemplate(request);
        } catch (TencentCloudSDKException e) {
            log.error("请求修改腾讯云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.UPDATE_FAIL);
        }
        Long templateId = response.getModifyTemplateStatus().getTemplateId();
        if (!EmptyUtil.isNullOrEmpty(templateId)) {
            //受理成功
            smsTemplateVo.setAcceptStatus(SuperConstant.YES);
            smsTemplateVo.setAcceptMsg("受理成功");
            //审核中
            smsTemplateVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsTemplateVo.setAuditMsg("审核中");
            smsTemplateVo.setTemplateCode(String.valueOf(templateId));
        }else {
            //受理失败
            smsTemplateVo.setAcceptStatus(SuperConstant.NO);
            smsTemplateVo.setAcceptMsg("受理失败");
            //重置审核状态
            smsTemplateVo.setAuditStatus(null);
            smsTemplateVo.setAuditMsg(null);
            smsTemplateVo.setTemplateCode(null);
        }
        //本地持久化
        SmsTemplate smsTemplate = BeanConv.toBean(smsTemplateVo, SmsTemplate.class);
        smsTemplate.setOtherConfig(JSONObject.toJSONString(smsTemplateVo.getOtherConfigs()));
        return smsTemplateService.updateById(smsTemplate);
    }

    private DescribeSmsTemplateListResponse query(SmsTemplateVo smsTemplateVo) {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        DescribeSmsTemplateListRequest request = new DescribeSmsTemplateListRequest();
        //模板 ID 数组。 注：默认数组最大长度100。
        List<Long> templateCodes = new ArrayList<>();
        templateCodes.add(Long.valueOf(smsTemplateVo.getTemplateCode()));
        request.setTemplateIdSet(templateCodes.stream().toArray(Long[]::new));
        request.setInternational(Long.valueOf(smsTemplateVo.getInternational()));
        // 返回的resp是一个DescribeSmsTemplateListResponse的实例，与请求对象对应
        SmsClient smsClient = tencentSmsConfig.queryClient();
        DescribeSmsTemplateListResponse response = null;
        try {
            response = smsClient.DescribeSmsTemplateList(request);
        } catch (TencentCloudSDKException e) {
            log.error("请求查询腾讯云模板出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
        return response;
    }

    @Override
    public Boolean querySmsTemplate(SmsTemplateVo smsTemplateVo) {
        DescribeSmsTemplateListResponse response = query(smsTemplateVo);
        //处理结果
        DescribeTemplateListStatus[] describeTemplateStatusSet = response.getDescribeTemplateStatusSet();
        response.getDescribeTemplateStatusSet();
        if (!EmptyUtil.isNullOrEmpty(describeTemplateStatusSet)){
            DescribeTemplateListStatus describeTemplateListStatus = describeTemplateStatusSet[0];
            Long statusCode = describeTemplateListStatus.getStatusCode();
            //审核通过
            if (statusCode == 0) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                smsTemplateVo.setAuditMsg("审核通过");
                return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
            //审核失败
            } else if (statusCode == -1) {
                smsTemplateVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                smsTemplateVo.setAuditMsg(describeTemplateListStatus.getReviewReply());
                return smsTemplateService.updateById(BeanConv.toBean(smsTemplateVo, SmsTemplate.class));
            } else {
                log.info("腾讯云模板：{},审核中", describeTemplateListStatus.getTemplateId());
                return true;
            }
        }else {
            log.warn("受理查询腾讯云签名出错");
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
    }
}
