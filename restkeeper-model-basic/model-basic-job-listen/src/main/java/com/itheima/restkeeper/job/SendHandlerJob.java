package com.itheima.restkeeper.job;

import com.itheima.restkeeper.adapter.SmsSendAdapter;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description：近期订单处理
 */
@Component
public class SendHandlerJob {

    @Autowired
    SmsSendAdapter smsSendAdapter;

    @Autowired
    ISmsSendRecordService smsSendRecordService;

    /***
     * @description 支付宝支付状态同步
     * @param param
     * @return
     */
    @XxlJob(value = "sendHandlerJob")
    public ReturnT<String> execute(String param) {
        List<SmsSendRecord> smsSendRecordList =  smsSendRecordService.CallBackSmsSendRecords();
        for (SmsSendRecord smsSendRecord : smsSendRecordList) {
            smsSendAdapter.querySendSms(smsSendRecord);
        }
        ReturnT.SUCCESS.setMsg("执行-短信发送同步-成功");
        return ReturnT.SUCCESS;
    }



}
