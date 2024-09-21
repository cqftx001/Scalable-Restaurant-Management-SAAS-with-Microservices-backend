package com.itheima.restkeeper.handler;

import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.req.SmsSignVo;

/**
 * @ClassName SmsSignHandler.java
 * @Description 签名处理器接口
 */
public interface SmsSignHandler {

    /***
     * @description 申请签名
     * @param smsSignVo 签名
     * @return 请求成功
     */
    SmsSign addSmsSign(SmsSignVo smsSignVo);

    /***
     * @description 删除签名
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean deleteSmsSign(SmsSignVo smsSignVo);

    /***
     * @description 修改签名
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean modifySmsSign(SmsSignVo smsSignVo);

    /***
     * @description 查询签名审核状态
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean querySmsSign(SmsSignVo smsSignVo);


}
