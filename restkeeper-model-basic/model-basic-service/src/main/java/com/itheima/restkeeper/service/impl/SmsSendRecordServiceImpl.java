package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.pojo.SmsSendRecord;
import com.itheima.restkeeper.mapper.SmsSendRecordMapper;
import com.itheima.restkeeper.req.SmsSendRecordVo;
import com.itheima.restkeeper.service.ISmsSendRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：发送记录表 服务实现类
 */
@Service
public class SmsSendRecordServiceImpl extends ServiceImpl<SmsSendRecordMapper, SmsSendRecord> implements ISmsSendRecordService {

    @Override
    public Page<SmsSendRecord> findSmsSendRecordVoPage(SmsSendRecordVo smsSendRecordVo, int pageNum, int pageSize) {
        Page<SmsSendRecord> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SmsSendRecord> queryWrapper = new QueryWrapper<>();

        if (!EmptyUtil.isNullOrEmpty(smsSendRecordVo.getMobile())) {
            queryWrapper.lambda().eq(SmsSendRecord::getMobile,smsSendRecordVo.getMobile());
        }
        if (!EmptyUtil.isNullOrEmpty(smsSendRecordVo.getChannelLabel())) {
            queryWrapper.lambda().eq(SmsSendRecord::getChannelLabel,smsSendRecordVo.getChannelLabel());
        }
        if (!EmptyUtil.isNullOrEmpty(smsSendRecordVo.getAcceptStatus())) {
            queryWrapper.lambda().eq(SmsSendRecord::getAcceptStatus,smsSendRecordVo.getAcceptStatus());
        }
        if (!EmptyUtil.isNullOrEmpty(smsSendRecordVo.getSendStatus())) {
            queryWrapper.lambda().eq(SmsSendRecord::getSendStatus,smsSendRecordVo.getSendStatus());
        }
        if (!EmptyUtil.isNullOrEmpty(smsSendRecordVo.getEnableFlag())) {
            queryWrapper.lambda().eq(SmsSendRecord::getEnableFlag,smsSendRecordVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByDesc(SmsSendRecord::getCreatedTime);
        return page(page, queryWrapper);
    }

    @Override
    public SmsSendRecord createSmsSendRecord(SmsSendRecordVo smsSendRecordVo) {
        SmsSendRecord smsSendRecord = BeanConv.toBean(smsSendRecordVo, SmsSendRecord.class);
        boolean flag = save(smsSendRecord);
        if (flag){
            return smsSendRecord;
        }
        return null;
    }

    @Override
    public Boolean updateSmsSendRecord(SmsSendRecordVo smsSendRecordVo) {
        SmsSendRecord smsSendRecord = BeanConv.toBean(smsSendRecordVo, SmsSendRecord.class);
        return updateById(smsSendRecord);
    }

    @Override
    public Boolean deleteSmsSendRecord(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        return removeByIds(idsLong);
    }

    @Override
    public List<SmsSendRecord> CallBackSmsSendRecords() {
        QueryWrapper<SmsSendRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsSendRecord::getAcceptStatus, SuperConstant.YES)
                .eq(SmsSendRecord::getSendStatus,SuperConstant.SENDING)
                .last("limit 0 , 20");;
        return list(queryWrapper);
    }
}
