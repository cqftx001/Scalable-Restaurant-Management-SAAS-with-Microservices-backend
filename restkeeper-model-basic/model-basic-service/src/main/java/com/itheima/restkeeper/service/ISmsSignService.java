package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.SmsSign;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.SmsSignVo;

import java.util.List;

/**
 * @Description： 签名服务类
 */
public interface ISmsSignService extends IService<SmsSign> {

    /**
     * @Description 签名列表
     * @param smsSignVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<SmsSignVo>
     */
    Page<SmsSign> findSmsSignVoPage(SmsSignVo smsSignVo, int pageNum, int pageSize);

    /***
     * @description 查询签名下拉框
     * @return
     */
    List<SmsSign> findSmsSignVoList();

    /***
     * @description 按签名和渠道查询签名信息
     * @param signName
     * @param channelLabel
     * @return
     */
    SmsSign findSmsSignBySignNameAndChannelLabel(String signName, String channelLabel);

    /***
     * @description 按签名No和渠道查询签名信息
     * @param signNo
     * @param channelLabel
     * @return
     */
    SmsSign findSmsSignBySignNoAndChannelLabel(String signNo, String channelLabel);

    /***
     * @description 按签名Code和渠道查询签名信息
     * @param signCode
     * @param channelLabel
     * @return
     */
    SmsSign findSmsSignBySignCodeAndChannelLabel(String signCode, String channelLabel);
}
