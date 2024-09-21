package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.SmsBlacklist;
import com.itheima.restkeeper.mapper.SmsBlacklistMapper;
import com.itheima.restkeeper.pojo.SmsBlacklist;
import com.itheima.restkeeper.req.SmsBlacklistVo;
import com.itheima.restkeeper.req.SmsBlacklistVo;
import com.itheima.restkeeper.service.ISmsBlacklistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：黑名单表 服务实现类
 */
@Service
public class SmsBlacklistServiceImpl extends ServiceImpl<SmsBlacklistMapper, SmsBlacklist> implements ISmsBlacklistService {

    @Override
    public Page<SmsBlacklist> findSmsBlacklistVoPage(SmsBlacklistVo smsBlacklistVo, int pageNum, int pageSize) {
        Page<SmsBlacklist> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SmsBlacklist> queryWrapper = new QueryWrapper<>();

        if (!EmptyUtil.isNullOrEmpty(smsBlacklistVo.getMobile())) {
            queryWrapper.lambda().eq(SmsBlacklist::getMobile,smsBlacklistVo.getMobile());
        }
        if (!EmptyUtil.isNullOrEmpty(smsBlacklistVo.getEnableFlag())) {
            queryWrapper.lambda().eq(SmsBlacklist::getEnableFlag,smsBlacklistVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByAsc(SmsBlacklist::getCreatedTime);
        return page(page, queryWrapper);
    }

    @Override
    public SmsBlacklist createSmsBlacklist(SmsBlacklistVo smsBlacklistVo) {
        SmsBlacklist smsBlacklist = BeanConv.toBean(smsBlacklistVo, SmsBlacklist.class);
        boolean flag = save(smsBlacklist);
        if (flag){
            return smsBlacklist;
        }
        return null;
    }

    @Override
    public Boolean updateSmsBlacklist(SmsBlacklistVo smsBlacklistVo) {
        SmsBlacklist smsBlacklist = BeanConv.toBean(smsBlacklistVo, SmsBlacklist.class);
        return updateById(smsBlacklist);
    }

    @Override
    public Boolean deleteSmsBlacklist(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        return removeByIds(idsLong);
    }
}
