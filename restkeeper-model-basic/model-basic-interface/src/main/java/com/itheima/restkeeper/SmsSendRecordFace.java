package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.SmsSendRecordVo;

/**
 * @ClassName SmsSendRecordFace.java
 * @Description TODO
 */
public interface SmsSendRecordFace {

    /**
     * @Description 发送记录列表
     * @param smsSendRecordVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<SmsSendRecordVo>
     */
    Page<SmsSendRecordVo> findSmsSendRecordVoPage(
            SmsSendRecordVo smsSendRecordVo,
            int pageNum,
            int pageSize)throws ProjectException;

    /**
     * @Description 创建发送记录
     * @param smsSendRecordVo 对象信息
     * @return SmsSendRecordVo
     */
    SmsSendRecordVo createSmsSendRecord(SmsSendRecordVo smsSendRecordVo)throws ProjectException;

    /**
     * @Description 修改发送记录
     * @param smsSendRecordVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsSendRecord(SmsSendRecordVo smsSendRecordVo)throws ProjectException;

    /**
     * @Description 删除发送记录
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deleteSmsSendRecord(String[] checkedIds)throws ProjectException;

    /**
     * @Description 查找发送记录
     * @param smsSendRecordId 选择对象信息Id
     * @return SmsSendRecordVo
     */
    SmsSendRecordVo findSmsSendRecordBySmsSendRecordId(Long smsSendRecordId)throws ProjectException;

    /**
     * @Description 重发
     * @param smsSendRecordVo 重发记录
     * @return SmsSendRecordVo
     */
    Boolean retrySendSms(SmsSendRecordVo smsSendRecordVo);
}
