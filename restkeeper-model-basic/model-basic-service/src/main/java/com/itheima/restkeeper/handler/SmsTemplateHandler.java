package com.itheima.restkeeper.handler;

import com.itheima.restkeeper.pojo.SmsTemplate;
import com.itheima.restkeeper.req.SmsTemplateVo;

/**
 * @ClassName SmsTemplateHandler.java
 * @Description 模板处理器接口
 */
public interface SmsTemplateHandler {

    /***
     * @description 申请模板
     * @param smsTemplateVo 模板信息
     * @return
     */
    SmsTemplate addSmsTemplate(SmsTemplateVo smsTemplateVo);

    /***
     * @description 删除模板
     * @param smsTemplateVo 模板信息
     * @return
     */
    Boolean deleteSmsTemplate(SmsTemplateVo smsTemplateVo);

    /***
     * @description 修改模板
     * @param smsTemplateVo 模板信息
     * @return
     */
    Boolean modifySmsTemplate(SmsTemplateVo smsTemplateVo);

    /***
     * @description 查询模板审核状态
     * @param smsTemplateVo 模板信息
     * @return
     */
    Boolean querySmsTemplate(SmsTemplateVo smsTemplateVo);


}
