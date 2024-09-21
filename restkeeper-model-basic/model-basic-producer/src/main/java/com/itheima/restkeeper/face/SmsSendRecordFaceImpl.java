package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsSendRecordFace;
import com.itheima.restkeeper.adapter.SmsSendAdapter;
import com.itheima.restkeeper.enums.SmsSendRecordEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.req.SmsSendRecordVo;
import com.itheima.restkeeper.req.SmsSendRecordVo;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName SmsSendRecordFaceImpl.java
 * @Description 发送记录dubbo实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findSmsSendRecordVoPage",retries = 2),
        @Method(name = "createSmsSendRecord",retries = 0),
        @Method(name = "updateSmsSendRecord",retries = 0),
        @Method(name = "deleteSmsSendRecord",retries = 0),
        @Method(name = "findSmsSendRecordBySmsSendRecordId",retries = 2),
        @Method(name = "retrySendSms",retries = 0),

})
public class SmsSendRecordFaceImpl implements SmsSendRecordFace {

    @Autowired
    ISmsSendRecordService smsSendRecordService;

    @Autowired
    SmsSendAdapter smsSendAdapter;

    @Override
    public Page<SmsSendRecordVo> findSmsSendRecordVoPage(
        SmsSendRecordVo smsSendRecordVo,
        int pageNum,
        int pageSize)throws ProjectException {
        try {
            Page<SmsSendRecord> page = smsSendRecordService.findSmsSendRecordVoPage(
                    smsSendRecordVo,
                    pageNum,
                    pageSize);
            Page<SmsSendRecordVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<SmsSendRecord> smsSendRecordList = page.getRecords();
            List<SmsSendRecordVo> smsSendRecordVoList = BeanConv.toBeanList(smsSendRecordList,SmsSendRecordVo.class);
            pageVo.setRecords(smsSendRecordVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询通道列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.PAGE_FAIL);
        }

    }

    @Override
    public SmsSendRecordVo createSmsSendRecord(SmsSendRecordVo smsSendRecordVo)throws ProjectException {
        try {
            return BeanConv.toBean( smsSendRecordService.createSmsSendRecord(smsSendRecordVo), SmsSendRecordVo.class);
        } catch (Exception e) {
            log.error("保存通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateSmsSendRecord(SmsSendRecordVo smsSendRecordVo)throws ProjectException {
        try {
            return smsSendRecordService.updateSmsSendRecord(smsSendRecordVo);
        } catch (Exception e) {
            log.error("保存通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteSmsSendRecord(String[] checkedIds) throws ProjectException{
        try {
            return smsSendRecordService.deleteSmsSendRecord(checkedIds);
        } catch (Exception e) {
            log.error("删除通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.DELETE_FAIL);
        }
    }

    @Override
    public SmsSendRecordVo findSmsSendRecordBySmsSendRecordId(Long smsSendRecordId)throws ProjectException {
        try {
            SmsSendRecord smsSendRecord = smsSendRecordService.getById(smsSendRecordId);
            if (!EmptyUtil.isNullOrEmpty(smsSendRecord)){
                return BeanConv.toBean(smsSendRecord,SmsSendRecordVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.SELECT_FAIL);
        }
    }

    @Override
    public Boolean retrySendSms(SmsSendRecordVo smsSendRecordVo) {
        try {
            return smsSendAdapter.retrySendSms(BeanConv.toBean(smsSendRecordVo,SmsSendRecord.class));
        } catch (Exception e) {
            log.error("查找通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSendRecordEnum.SEND_RECORD_FAIL);
        }
    }
}
