package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.mapper.RefundRecordMapper;
import com.itheima.restkeeper.service.IRefundRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

/**
 * @Description： 退款记录服务实现类
 */
@Service
public class RefundRecordServiceImpl extends ServiceImpl<RefundRecordMapper, RefundRecord> implements IRefundRecordService {

    @Override
    public RefundRecord findRefundRecordByProductOrderNoAndSending(Long productOrderNo) {
        QueryWrapper<RefundRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RefundRecord::getProductOrderNo, productOrderNo)
                .eq(RefundRecord::getRefundStatus, TradingConstant.REFUND_STATUS_SENDING);
        return getOne(queryWrapper);
    }
}
