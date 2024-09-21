package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.PayChannel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.PayChannelVo;

import java.util.List;

/**
 * @Description： 支付通道服务类
 */
public interface IPayChannelService extends IService<PayChannel> {

    /**
     * @Description 支付通道列表
     * @param payChannelVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 当前页
     * @return Page<PayChannel>
     */
    Page<PayChannel> findPayChannelVoPage(PayChannelVo payChannelVo, int pageNum, int pageSize);

    /**
     * @Description 创建支付通道
     * @param payChannelVo 对象信息
     * @return PayChannel
     */
    PayChannel createPayChannel(PayChannelVo payChannelVo);

    /**
     * @Description 修改支付通道
     * @param payChannelVo 对象信息
     * @return Boolean
     */
    Boolean updatePayChannel(PayChannelVo payChannelVo);

    /**
     * @Description 删除支付通道
     * @param checkedIds 选择的支付通道ID
     * @return Boolean
     */
    Boolean deletePayChannel(String[] checkedIds);

    /**
     * @Description 删除支付通道
     * @param channelLabel 支付通道标识
     * @return Boolean
     */
    List<PayChannel> findPayChannelList(String channelLabel);
}
