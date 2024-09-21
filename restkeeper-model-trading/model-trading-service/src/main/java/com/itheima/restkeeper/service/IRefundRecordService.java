package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.RefundRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description： 退款记录表服务类
 */
public interface IRefundRecordService extends IService<RefundRecord> {

    /***
     * @description 查询当前订单是否有退款中的记录
     *
     * @param productOrderNo
     * @return
     */
    RefundRecord findRefundRecordByProductOrderNoAndSending(Long productOrderNo);
}
