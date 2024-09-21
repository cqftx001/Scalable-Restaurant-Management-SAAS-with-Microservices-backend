package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.req.SmsSignVo;

import java.util.List;

/**
 * @ClassName SmsSignFace.java
 * @Description 签名dubbo接口
 */
public interface SmsSignFace {

    /**
     * @Description 签名列表
     * @param smsSignVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<SmsSignVo>
     */
    Page<SmsSignVo> findSmsSignVoPage(SmsSignVo smsSignVo,
                                      int pageNum,
                                      int pageSize)throws ProjectException;

    /***
     * @description 申请签名
     * @param smsSignVo 签名
     * @return 请求成功
     */
    SmsSignVo addSmsSign(SmsSignVo smsSignVo)throws ProjectException ;

    /***
     * @description 删除签名
     * @param checkedIds 签名ids
     * @return 请求成功
     */
    Boolean deleteSmsSign(String[] checkedIds) throws ProjectException;

    /***
     * @description 修改签名
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean modifySmsSign(SmsSignVo smsSignVo)throws ProjectException ;

    /***
     * @description 修改签名状态
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean updateSmsSignEnableFlag(SmsSignVo smsSignVo)throws ProjectException ;

    /***
     * @description 查询签名审核状态
     * @param smsSignVo 签名
     * @return 请求成功
     */
    Boolean querySmsSign(SmsSignVo smsSignVo)throws ProjectException ;


    /***
     * @description 查询签名下拉框
     * @return
     */
    List<SmsSignVo> findSmsSignVoList()throws ProjectException;
}
