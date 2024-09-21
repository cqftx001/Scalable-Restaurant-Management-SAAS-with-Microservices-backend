package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.mapper.TradingMapper;
import com.itheima.restkeeper.service.ITradingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：交易订单表 服务实现类
 */
@Service
public class TradingServiceImpl extends ServiceImpl<TradingMapper, Trading> implements ITradingService {

    @Override
    public Trading findTradByTradingOrderNo(Long tradingOrderNo) {
        QueryWrapper<Trading> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Trading::getTradingOrderNo,tradingOrderNo);
        return getOne(queryWrapper);
    }

    @Override
    public Trading findTradByProductOrderNo(Long productOrderNo) {
        QueryWrapper<Trading> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Trading::getProductOrderNo,productOrderNo);
        return getOne(queryWrapper);
    }

    @Override
    public List<Trading> findTradingByTradingState(String tradingState) {
        QueryWrapper<Trading> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Trading::getTradingState,tradingState)
        .eq(Trading::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }
}
