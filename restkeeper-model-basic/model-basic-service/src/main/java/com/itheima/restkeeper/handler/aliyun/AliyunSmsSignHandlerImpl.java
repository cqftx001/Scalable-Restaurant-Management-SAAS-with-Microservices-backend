package com.itheima.restkeeper.handler.aliyun;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.SmsSignHandler;
import com.itheima.restkeeper.handler.aliyun.config.AliyunSmsConfig;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SmsSignAdapter.java
 * @Description 阿里云签名处理器接口
 */
@Slf4j
@Service("aliyunSmsSignHandler")
public class AliyunSmsSignHandlerImpl implements SmsSignHandler {

    @Autowired
    ISmsSignService smsSignService;

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    @Override
    public SmsSign addSmsSign(SmsSignVo smsSignVo){
        //查询当前签名是否保存过
        SmsSign smsSignHandler = smsSignService.findSmsSignBySignNameAndChannelLabel
                        (smsSignVo.getSignName(),smsSignVo.getChannelLabel());
        if (!EmptyUtil.isNullOrEmpty(smsSignHandler)){
            smsSignVo = BeanConv.toBean(smsSignHandler,SmsSignVo.class);
            //查询当前签名在远程的是否存在
            QuerySmsSignResponse querySmsSignResponse = query(smsSignVo);
            String codeQuery = querySmsSignResponse.getBody().getCode();
            if ("OK".equals(codeQuery)){
                Integer SignStatus =querySmsSignResponse.getBody().getSignStatus();
                //受理成功
                smsSignVo.setAcceptStatus(SuperConstant.YES);
                smsSignVo.setAcceptMsg("受理成功");
                smsSignVo.setSignCode(smsSignVo.getSignName());
                //审核通过
                if (SignStatus==1){
                    smsSignVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                    smsSignVo.setAuditMsg("审核通过");
                //审核失败
                }else if (SignStatus==2){
                    smsSignVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                    smsSignVo.setAuditMsg(querySmsSignResponse.getBody().getReason());
                }else {
                    smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
                    smsSignVo.setAuditMsg(querySmsSignResponse.getBody().getReason());
                }
                SmsSign smsSign = BeanConv.toBean(smsSignVo, SmsSign.class);
                boolean flag = smsSignService.saveOrUpdate(smsSign);
                if (flag){
                    return smsSign;
                }
                throw new ProjectException(SmsSignEnum.CREATE_FAIL);
            }
        }
        AddSmsSignRequest addSmsSignRequest = new AddSmsSignRequest();
        //签名名称
        addSmsSignRequest.setSignName(smsSignVo.getSignName());
        //签名来源。取值：
        //0：企事业单位的全称或简称。
        //1：工信部备案网站的全称或简称。
        //2：App应用的全称或简称。
        //3：公众号或小程序的全称或简称。
        //4：电商平台店铺名的全称或简称。
        //5：商标名的全称或简称。
        addSmsSignRequest.setSignSource(Integer.valueOf(smsSignVo.getSignType()));
        //短信签名申请说明。请在申请说明中详细描述您的业务使用场景，
        //申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符。
        addSmsSignRequest.setRemark(smsSignVo.getRemark());
        //附件信息
        List<AddSmsSignRequest.AddSmsSignRequestSignFileList> signFileList = new ArrayList<>();
        String[] proofTypes = smsSignVo.getProofType().split("@");
        String[] proofImages = smsSignVo.getProofImage().split("@");
        for (int i = 0 ; i<proofImages.length;i++) {
            AddSmsSignRequest.AddSmsSignRequestSignFileList signFile = new AddSmsSignRequest.AddSmsSignRequestSignFileList();
            signFile.setFileContents(proofImages[i]);
            signFile.setFileSuffix(proofTypes[i]);
            signFileList.add(signFile);
        }
        addSmsSignRequest.setSignFileList(signFileList);
        // 复制代码运行请自行打印 API 的返回值
        Client client =aliyunSmsConfig.queryClient();
        AddSmsSignResponse response = null;
        try {
            response = client.addSmsSign(addSmsSignRequest);
        } catch (Exception e) {
            log.error("请求添加阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.CREATE_FAIL);
        }
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            //受理成功
            smsSignVo.setAcceptStatus(SuperConstant.YES);
            smsSignVo.setAcceptMsg("受理成功");
            smsSignVo.setSignCode(smsSignVo.getSignName());
            //审核中
            smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsSignVo.setAuditMsg("审核中");
            smsSignVo.setSignCode(smsSignVo.getSignName());
        }else {
            smsSignVo.setAcceptStatus(SuperConstant.NO);
            smsSignVo.setAcceptMsg(message);
        }
        SmsSign smsSign = BeanConv.toBean(smsSignVo, SmsSign.class);
        boolean flag = smsSignService.save(smsSign);
        if (flag){
            return smsSign;
        }
        throw new ProjectException(SmsSignEnum.CREATE_FAIL);
    }

    @Override
    public Boolean deleteSmsSign(SmsSignVo smsSignVo){
        DeleteSmsSignRequest deleteSmsSignRequest = new DeleteSmsSignRequest();;
        deleteSmsSignRequest.setSignName(smsSignVo.getSignName());
        Client client =aliyunSmsConfig.queryClient();
        DeleteSmsSignResponse response = null;
        try {
            response = client.deleteSmsSign(deleteSmsSignRequest);
        } catch (Exception e) {
            log.error("请求删除阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.DELETE_FAIL);
        }
        return smsSignService.removeById(smsSignVo.getId());
    }

    @Override
    public Boolean modifySmsSign(SmsSignVo smsSignVo){
        ModifySmsSignRequest modifySmsSignRequest = new ModifySmsSignRequest();
        //签名名称
        modifySmsSignRequest.setSignName(smsSignVo.getSignName());
        //签名来源。取值：
        //0：企事业单位的全称或简称。
        //1：工信部备案网站的全称或简称。
        //2：App应用的全称或简称。
        //3：公众号或小程序的全称或简称。
        //4：电商平台店铺名的全称或简称。
        //5：商标名的全称或简称。
        modifySmsSignRequest.setSignSource(Integer.valueOf(smsSignVo.getSignType()));
        //短信签名申请说明。请在申请说明中详细描述您的业务使用场景，
        //申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符。
        modifySmsSignRequest.setRemark(smsSignVo.getRemark());
        //附件信息
        List<ModifySmsSignRequest.ModifySmsSignRequestSignFileList> signFileList = new ArrayList<>();
        String[] proofTypes = smsSignVo.getProofType().split("@");
        String[] proofImages = smsSignVo.getProofImage().split("@");
        for (int i = 0 ; i<proofImages.length;i++) {
            ModifySmsSignRequest.ModifySmsSignRequestSignFileList signFile = new ModifySmsSignRequest.ModifySmsSignRequestSignFileList();
            signFile.setFileContents(proofImages[i]);
            signFile.setFileSuffix(proofTypes[i]);
            signFileList.add(signFile);
        }
        modifySmsSignRequest.setSignFileList(signFileList);
        Client client =aliyunSmsConfig.queryClient();
        ModifySmsSignResponse response = null;
        try {
            response = client.modifySmsSign(modifySmsSignRequest);
        } catch (Exception e) {
            log.error("请求修改阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.UPDATE_FAIL);
        }
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            //受理成功
            smsSignVo.setAcceptStatus(SuperConstant.YES);
            smsSignVo.setAcceptMsg("受理成功");
            //审核中
            smsSignVo.setAuditStatus(SuperConstant.STATUS_IN_AUDIT);
            smsSignVo.setAuditMsg("审核中");
            smsSignVo.setSignCode(smsSignVo.getSignName());
        }else {
            //受理失败
            smsSignVo.setAcceptStatus(SuperConstant.NO);
            smsSignVo.setAcceptMsg(message);
            //重置审核状态
            smsSignVo.setAuditStatus(null);
            smsSignVo.setAuditMsg(null);
            smsSignVo.setSignCode(null);
        }
        return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
    }

    private QuerySmsSignResponse query(SmsSignVo smsSignVo) {
        QuerySmsSignRequest querySmsSignRequest = new QuerySmsSignRequest();
        querySmsSignRequest.setSignName(smsSignVo.getSignName());
        // 复制代码运行请自行打印 API 的返回值
        Client client =aliyunSmsConfig.queryClient();
        QuerySmsSignResponse response = null;
        try {
            response = client.querySmsSign(querySmsSignRequest);
        } catch (Exception e) {
            log.error("请求查询阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
        return response;
    }

    @Override
    public Boolean querySmsSign(SmsSignVo smsSignVo){
        QuerySmsSignResponse response =query(smsSignVo);
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        if ("OK".equals(code)){
            Integer SignStatus =response.getBody().getSignStatus();
            //审核通过
            if (SignStatus==1){
                smsSignVo.setAuditStatus(SuperConstant.STATUS_PASS_AUDIT);
                smsSignVo.setAuditMsg("审核通过");
                return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
            //审核失败
            }else if (SignStatus==2){
                smsSignVo.setAuditStatus(SuperConstant.STATUS_FAIL_AUDIT);
                smsSignVo.setAuditMsg(response.getBody().getReason());
                return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
            }else {
                log.info("阿里云签名：{},审核中", response.getBody().getSignName());
                return true;
            }
        }else {
            log.warn("受理查询阿里云签名出错：{}", message);
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
    }
}
