package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.SmsChannel;
import com.itheima.restkeeper.req.SmsChannelVo;

import java.util.List;
import java.util.Set;

/**
 * @Description： 服务类
 */
public interface ISmsChannelService extends IService<SmsChannel> {

    /***
     * @description 查询通道配置
     * @param channelLabel 通道标识
     * @return 通道
     */
    SmsChannel findChannelByChannelLabel(String channelLabel);

    /***
     * @description 查询通道配置
     * @param channelLabels 通道标识
     * @return 通道
     */
    List<SmsChannel> findChannelInChannelLabel(Set<String> channelLabels);

    /**
     * @Description 通道列表
     * @param smsChannelVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 当前页
     * @return Page<SmsChannel>
     */
    Page<SmsChannel> findSmsChannelVoPage(SmsChannelVo smsChannelVo, int pageNum, int pageSize);

    /**
     * @Description 创建通道
     * @param smsChannelVo 对象信息
     * @return SmsChannel
     */
    SmsChannel createSmsChannel(SmsChannelVo smsChannelVo);

    /**
     * @Description 修改通道
     * @param smsChannelVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsChannel(SmsChannelVo smsChannelVo);

    /**
     * @Description 删除通道
     * @param checkedIds 选择的通道ID
     * @return Boolean
     */
    Boolean deleteSmsChannel(String[] checkedIds);

    /***
     * @description 查询通道下拉框
     * @return: List<SmsChannel>
     */
    List<SmsChannel> findSmsChannelVoList();


}
