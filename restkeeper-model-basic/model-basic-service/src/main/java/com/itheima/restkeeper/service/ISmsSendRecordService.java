package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.SmsSendRecordVo;

import java.util.List;

/**
 * @Description：发送记录表 服务类
 */
public interface ISmsSendRecordService extends IService<SmsSendRecord> {

    /**
     * @Description 发送记录列表
     * @param smsSendRecordVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 当前页
     * @return Page<SmsSendRecord>
     */
    Page<SmsSendRecord> findSmsSendRecordVoPage(
            SmsSendRecordVo smsSendRecordVo,
            int pageNum,
            int pageSize);

    /**
     * @Description 创建发送记录
     * @param smsSendRecordVo 对象信息
     * @return SmsSendRecord
     */
    SmsSendRecord createSmsSendRecord(SmsSendRecordVo smsSendRecordVo);

    /**
     * @Description 修改发送记录
     * @param smsSendRecordVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsSendRecord(SmsSendRecordVo smsSendRecordVo);

    /**
     * @Description 删除发送记录
     * @param checkedIds 选择的发送记录ID
     * @return Boolean
     */
    Boolean deleteSmsSendRecord(String[] checkedIds);

    /***
     * @description 查询受理成功发送中状态的前20条短信
     * @return
     * @return: java.util.List<com.itheima.restkeeper.pojo.SmsSendRecord>
     */
    List<SmsSendRecord> CallBackSmsSendRecords();
}
