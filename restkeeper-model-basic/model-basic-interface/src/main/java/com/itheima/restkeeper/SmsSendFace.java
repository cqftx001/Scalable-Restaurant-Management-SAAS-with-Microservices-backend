package com.itheima.restkeeper;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.SendMessageVo;
import com.itheima.restkeeper.req.SmsSendRecordVo;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @ClassName SmsSendFace.java
 * @Description 邮件发送dubbo接口
 */
public interface SmsSendFace {

    /***
     * @description 发送短信接口
     * @param sendMessageVo 发送对象
     */
    Boolean SendSms(SendMessageVo sendMessageVo) throws ProjectException;

    /***
     * @description 查询短信接受情况
     * @param smsSendRecordVo 发送记录
     * @return
     */
    Boolean querySendSms(SmsSendRecordVo smsSendRecordVo) throws ProjectException;

    /***
     * @description 重试发送
     * @param smsSendRecordVo
     * @return
     */
    Boolean retrySendSms(SmsSendRecordVo smsSendRecordVo) throws ProjectException;
}
