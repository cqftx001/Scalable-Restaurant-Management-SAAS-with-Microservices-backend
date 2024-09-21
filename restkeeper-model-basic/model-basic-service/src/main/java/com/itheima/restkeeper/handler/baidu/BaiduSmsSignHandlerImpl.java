package com.itheima.restkeeper.handler.baidu;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.v3.*;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSignHandler;
import com.itheima.restkeeper.handler.baidu.config.BaiduSmsConfig;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @ClassName BaiduSmsSignHandlerImpl.java
 * @Description 百度签名审核
 */
@Service("baiduSmsSignHandler")
public class BaiduSmsSignHandlerImpl implements SmsSignHandler {

    @Autowired
    BaiduSmsConfig baiduSmsConfig;

    @Autowired
    ISmsSignService smsSignService;

    @Override
    public SmsSign addSmsSign(SmsSignVo smsSignVo) {
        //查询当前签名是否保存过
        SmsSign smsSignHandler = smsSignService.findSmsSignBySignNameAndChannelLabel
                (smsSignVo.getSignName(),smsSignVo.getChannelLabel());
        if (!EmptyUtil.isNullOrEmpty(smsSignHandler)){
            smsSignVo = BeanConv.toBean(smsSignHandler,SmsSignVo.class);
            //查询当前签名在远程的信息
            GetSignatureResponse getSignatureResponse = query(smsSignVo);
            //处理返回结果
            String getSignatureStatus = getSignatureResponse.getStatus();
            //审核通过
            if ("READY".equals(getSignatureStatus)) {
                smsSignVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                smsSignVo.setAuditMsg("审核通过");
                //审核失败
            } else if ("REJECTED".equals(getSignatureStatus) || "ABORTED".equals(getSignatureStatus)) {
                smsSignVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                smsSignVo.setAuditMsg(getSignatureResponse.getReview());
            } else {
                smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
                smsSignVo.setAuditMsg(getSignatureResponse.getReview());
            }
            SmsSign smsSign = BeanConv.toBean(smsSignVo, SmsSign.class);
            boolean flag = smsSignService.saveOrUpdate(smsSign);
            if (flag){
                return smsSign;
            }
            throw new ProjectException(SmsSignEnum.CREATE_FAIL);
        }
        CreateSignatureRequest request = new CreateSignatureRequest()
            //签名内容
            .withContent(smsSignVo.getSignName())
            //签名类型。
            //Enterprise：企业
            //MobileApp：移动应用名称
            //Web：工信部备案的网站名称
            //WeChatPublic：微信公众号名称
            //Brand：商标名称
            //Else：其他
            .withContentType(smsSignVo.getSignType())
            //签名适用的国家类型
            //DOMESTIC：国内
            //INTERNATIONAL：国际/港澳台
            //GLOBAL：全球均适用
            //默认为DOMESTIC
            .withCountryType(smsSignVo.getInternational())
            //对于签名的描述
            .withDescription(smsSignVo.getRemark())
            //签名的证明文件经过base64编码后的字符串。文件大小不超过2MB。
            .withSignatureFileBase64(smsSignVo.getProofImage())
            //签名证明文件的格式，目前支持JPG、PNG、JPEG三种格式
            .withSignatureFileFormat(smsSignVo.getProofType().replace(".",""));
        SmsClient client = baiduSmsConfig.queryClient();
        CreateSignatureResponse response = client.createSignature(request);
        String status = response.getStatus();
        if ("SUBMITTED".equals(status)) {
            //受理成功
            smsSignVo.setAcceptStatus(SuperConstant.YES);
            smsSignVo.setAcceptMsg("受理成功");
            //审核中
            smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsSignVo.setAuditMsg("审核中");
            smsSignVo.setSignCode(response.getSignatureId());

        } else {
            smsSignVo.setAcceptStatus(SuperConstant.NO);
            smsSignVo.setAcceptMsg(response.getStatus());
        }
        //本地持久化
        SmsSign smsSign = BeanConv.toBean(smsSignVo, SmsSign.class);
        boolean flag = smsSignService.save(smsSign);
        if (flag){
            return smsSign;
        }
        throw new ProjectException(SmsSignEnum.CREATE_FAIL);
    }

    @Override
    public Boolean deleteSmsSign(SmsSignVo smsSignVo) {
        DeleteSignatureRequest request = new DeleteSignatureRequest()
            //短信模板唯一识别码
            .withSignatureId(smsSignVo.getSignCode());
        SmsClient client = baiduSmsConfig.queryClient();
        client.deleteSignature(request);
        return smsSignService.removeById(smsSignVo.getId());
    }

    @Override
    public Boolean modifySmsSign(SmsSignVo smsSignVo) {
        ModifySignatureRequest request = new ModifySignatureRequest()
            .withSignatureId(smsSignVo.getSignCode())
            //签名内容
            .withContent(smsSignVo.getSignName())
            //签名类型。
            //Enterprise：企业
            //MobileApp：移动应用名称
            //Web：工信部备案的网站名称
            //WeChatPublic：微信公众号名称
            //Brand：商标名称
            //Else：其他
            .withContentType(smsSignVo.getSignType())
            //签名适用的国家类型
            //DOMESTIC：国内
            //INTERNATIONAL：国际/港澳台
            //GLOBAL：全球均适用
            //默认为DOMESTIC
            .withCountryType(smsSignVo.getInternational())
            //对于签名的描述
            .withDescription(smsSignVo.getRemark())
            //签名的证明文件经过base64编码后的字符串。文件大小不超过2MB。
            .withSignatureFileBase64(smsSignVo.getProofImage())
            //签名证明文件的格式，目前支持JPG、PNG、JPEG三种格式
            .withSignatureFileFmt(smsSignVo.getProofType().replace(",",""));
        SmsClient client = baiduSmsConfig.queryClient();
        client.modifySignature(request);
        //受理成功
        smsSignVo.setAcceptStatus(SuperConstant.YES);
        smsSignVo.setAcceptMsg("受理成功");
        //审核中
        smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
        smsSignVo.setAuditMsg("审核中");
        //本地持久化
        return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
    }

    private GetSignatureResponse query(SmsSignVo smsSignVo) {
        GetSignatureRequest request = new GetSignatureRequest();
        //查询
        request.setSignatureId(smsSignVo.getSignCode());
        SmsClient client = baiduSmsConfig.queryClient();
        return client.getSignature(request);
    }

    @Override
    public Boolean querySmsSign(SmsSignVo smsSignVo) {
        GetSignatureResponse response = query(smsSignVo);
        //处理返回结果
        String status = response.getStatus();
        //审核通过
        if ("READY".equals(status)) {
            smsSignVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
            smsSignVo.setAuditMsg("审核通过");
            return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
            //审核失败
        } else if ("REJECTED".equals(status) || "ABORTED".equals(status)) {
            smsSignVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
            smsSignVo.setAuditMsg(response.getReview());
            return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
        } else {
            return true;
        }
    }
}
