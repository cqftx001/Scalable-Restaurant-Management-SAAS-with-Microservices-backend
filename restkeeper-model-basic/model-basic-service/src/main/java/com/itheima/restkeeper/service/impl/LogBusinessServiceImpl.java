package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.LogBusiness;
import com.itheima.restkeeper.mapper.LogBusinessMapper;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.service.ILogBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description： 服务实现类
 */
@Service
public class LogBusinessServiceImpl extends ServiceImpl<LogBusinessMapper, LogBusiness> implements ILogBusinessService {

    @Override
    public Page<LogBusiness> findLogBusinessVoPage(LogBusinessVo logBusinessVo, int pageNum, int pageSize) {
        Page<LogBusiness> page = new Page<>(pageNum,pageSize);
        QueryWrapper<LogBusiness> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getHostAddress())) {
            queryWrapper.lambda().likeRight(LogBusiness::getHostAddress,logBusinessVo.getHostAddress());
        }
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getUserId())) {
            queryWrapper.lambda().eq(LogBusiness::getUserId,logBusinessVo.getUserId());
        }
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getHost())) {
            queryWrapper.lambda().likeRight(LogBusiness::getHost,logBusinessVo.getHost());
        }
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getRequestMethod())) {
            queryWrapper.lambda().eq(LogBusiness::getRequestMethod,logBusinessVo.getRequestMethod());
        }
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getResponseCode())) {
            queryWrapper.lambda().eq(LogBusiness::getResponseCode,logBusinessVo.getResponseCode());
        }
        List<String> createdTimeQuerty = logBusinessVo.getCreatedTimeQuerty();
        if (!EmptyUtil.isNullOrEmpty(logBusinessVo.getCreatedTimeQuerty())){
            queryWrapper.lambda().between(LogBusiness::getCreatedTime,createdTimeQuerty.get(0),createdTimeQuerty.get(1));
        }
        queryWrapper.lambda().orderByDesc(LogBusiness::getCreatedTime);
        return page(page, queryWrapper);
    }
}
