package com.itheima.restkeeper.face;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.PayChannelFace;
import com.itheima.restkeeper.enums.PayChannelEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.PayChannel;
import com.itheima.restkeeper.req.OtherConfigVo;
import com.itheima.restkeeper.req.PayChannelVo;
import com.itheima.restkeeper.req.PayChannelVo;
import com.itheima.restkeeper.service.IPayChannelService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PayChannelFaceImpl.java
 * @Description 支付渠道dubbo接口接口实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findPayChannelVoPage",retries = 2),
        @Method(name = "createPayChannel",retries = 0),
        @Method(name = "updatePayChannel",retries = 0),
        @Method(name = "deletePayChannel",retries = 0)
    })
public class PayChannelFaceImpl implements PayChannelFace {

    @Autowired
    IPayChannelService sayChannelService;


    @Override
    public Page<PayChannelVo> findPayChannelVoPage(PayChannelVo payChannelVo,
                                                   int pageNum,
                                                   int pageSize) throws ProjectException{
        try {
            Page<PayChannel> page = sayChannelService.findPayChannelVoPage(payChannelVo, pageNum, pageSize);
            Page<PayChannelVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<PayChannel> sayChannelList = page.getRecords();
            List<PayChannelVo> payChannelVoList = new ArrayList<>();
            if (!EmptyUtil.isNullOrEmpty(sayChannelList)){
                sayChannelList.forEach(n->{
                    PayChannelVo payChannelVoHandler = BeanConv.toBean(n, PayChannelVo.class);
                    if (!EmptyUtil.isNullOrEmpty(n.getOtherConfig())){
                        List <OtherConfigVo> list = JSONArray.parseArray(n.getOtherConfig(),OtherConfigVo.class);
                        payChannelVoHandler.setOtherConfigs(list);
                    }
                    payChannelVoList.add(payChannelVoHandler);
                });
            }
            pageVo.setRecords(payChannelVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询支付通道列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PayChannelEnum.PAGE_FAIL);
        }

    }

    @Override
    @Transactional
    public PayChannelVo createPayChannel(PayChannelVo payChannelVo) throws ProjectException{
        try {
            return BeanConv.toBean( sayChannelService.createPayChannel(payChannelVo), PayChannelVo.class);
        } catch (Exception e) {
            log.error("保存支付通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PayChannelEnum.CREATE_FAIL);
        }
    }

    @Override
    @Transactional
    public Boolean updatePayChannel(PayChannelVo payChannelVo) throws ProjectException{
        try {
            return sayChannelService.updatePayChannel(payChannelVo);
        } catch (Exception e) {
            log.error("保存支付通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PayChannelEnum.UPDATE_FAIL);
        }
    }

    @Override
    @Transactional
    public Boolean deletePayChannel(String[] checkedIds) throws ProjectException{
        try {
            return sayChannelService.deletePayChannel(checkedIds);
        } catch (Exception e) {
            log.error("删除支付通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PayChannelEnum.DELETE_FAIL);
        }
    }


}
