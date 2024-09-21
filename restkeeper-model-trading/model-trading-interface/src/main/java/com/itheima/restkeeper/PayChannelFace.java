package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.PayChannelVo;

import java.util.List;

/**
 * @ClassName PayChannelFace.java
 * @Description 渠道dubbo接口
 */
public interface PayChannelFace {

    /**
     * @Description 渠道列表
     * @param smsChannelVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<PayChannelVo>
     */
    Page<PayChannelVo> findPayChannelVoPage(PayChannelVo smsChannelVo,
                                            int pageNum,
                                            int pageSize)throws ProjectException;

    /**
     * @Description 创建渠道
     * @param smsChannelVo 对象信息
     * @return PayChannelVo
     */
    PayChannelVo createPayChannel(PayChannelVo smsChannelVo)throws ProjectException;

    /**
     * @Description 修改渠道
     * @param smsChannelVo 对象信息
     * @return Boolean
     */
    Boolean updatePayChannel(PayChannelVo smsChannelVo)throws ProjectException;

    /**
     * @Description 删除渠道
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deletePayChannel(String[] checkedIds)throws ProjectException;


}
