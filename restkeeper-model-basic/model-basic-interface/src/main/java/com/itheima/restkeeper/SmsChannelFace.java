package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.SmsChannelVo;

import java.util.List;

/**
 * @ClassName SmsChannelFace.java
 * @Description 渠道dubbo接口
 */
public interface SmsChannelFace {

    /**
     * @Description 渠道列表
     * @param smsChannelVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<SmsChannelVo>
     */
    Page<SmsChannelVo> findSmsChannelVoPage(SmsChannelVo smsChannelVo,
                                            int pageNum,
                                            int pageSize)throws ProjectException;

    /**
     * @Description 创建渠道
     * @param smsChannelVo 对象信息
     * @return SmsChannelVo
     */
    SmsChannelVo createSmsChannel(SmsChannelVo smsChannelVo)throws ProjectException;

    /**
     * @Description 修改渠道
     * @param smsChannelVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsChannel(SmsChannelVo smsChannelVo)throws ProjectException;

    /**
     * @Description 删除渠道
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deleteSmsChannel(String[] checkedIds)throws ProjectException;

    /**
     * @Description 查找渠道
     * @param smsChannelId 选择对象信息Id
     * @return SmsChannelVo
     */
    SmsChannelVo findSmsChannelBySmsChannelId(Long smsChannelId)throws ProjectException;

    /***
     * @description 查询渠道下拉框
     * @return: List<SmsChannelVo>
     */
    List<SmsChannelVo> findSmsChannelVoList()throws ProjectException;
    
}
