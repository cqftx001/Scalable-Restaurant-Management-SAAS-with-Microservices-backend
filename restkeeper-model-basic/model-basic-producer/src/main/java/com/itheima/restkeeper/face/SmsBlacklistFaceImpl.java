package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsBlacklistFace;
import com.itheima.restkeeper.enums.SmsBlacklistEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.SmsBlacklist;
import com.itheima.restkeeper.req.SmsBlacklistVo;
import com.itheima.restkeeper.req.SmsBlacklistVo;
import com.itheima.restkeeper.service.ISmsBlacklistService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName SmsBlacklistFaceImpl.java
 * @Description 黑名单dubbo实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findSmsBlacklistVoPage",retries = 2),
        @Method(name = "createSmsBlacklist",retries = 0),
        @Method(name = "updateSmsBlacklist",retries = 0),
        @Method(name = "deleteSmsBlacklist",retries = 0),
        @Method(name = "findSmsBlacklistBySmsBlacklistId",retries = 2)
    })
public class SmsBlacklistFaceImpl implements SmsBlacklistFace {

    @Autowired
    ISmsBlacklistService smsBlacklistService;

    @Override
    public Page<SmsBlacklistVo> findSmsBlacklistVoPage(SmsBlacklistVo smsBlacklistVo,
                                                       int pageNum,
                                                       int pageSize)throws ProjectException {
        try {
            Page<SmsBlacklist> page = smsBlacklistService.findSmsBlacklistVoPage(smsBlacklistVo, pageNum, pageSize);
            Page<SmsBlacklistVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<SmsBlacklist> smsBlacklistList = page.getRecords();
            List<SmsBlacklistVo> smsBlacklistVoList = BeanConv.toBeanList(smsBlacklistList,SmsBlacklistVo.class);
            pageVo.setRecords(smsBlacklistVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询通道列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsBlacklistEnum.PAGE_FAIL);
        }

    }

    @Override
    public SmsBlacklistVo createSmsBlacklist(SmsBlacklistVo smsBlacklistVo)throws ProjectException {
        try {
            return BeanConv.toBean( smsBlacklistService.createSmsBlacklist(smsBlacklistVo), SmsBlacklistVo.class);
        } catch (Exception e) {
            log.error("保存通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsBlacklistEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateSmsBlacklist(SmsBlacklistVo smsBlacklistVo)throws ProjectException {
        try {
            return smsBlacklistService.updateSmsBlacklist(smsBlacklistVo);
        } catch (Exception e) {
            log.error("保存通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsBlacklistEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteSmsBlacklist(String[] checkedIds) throws ProjectException{
        try {
            return smsBlacklistService.deleteSmsBlacklist(checkedIds);
        } catch (Exception e) {
            log.error("删除通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsBlacklistEnum.DELETE_FAIL);
        }
    }

    @Override
    public SmsBlacklistVo findSmsBlacklistBySmsBlacklistId(Long smsBlacklistId)throws ProjectException {
        try {
            SmsBlacklist smsBlacklist = smsBlacklistService.getById(smsBlacklistId);
            if (!EmptyUtil.isNullOrEmpty(smsBlacklist)){
                return BeanConv.toBean(smsBlacklist,SmsBlacklistVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsBlacklistEnum.SELECT_FAIL);
        }
    }
    
}
