package com.itheima.restkeeper.face;

import com.itheima.restkeeper.TradingFace;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.BeanConv;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName TradingFace.java
 * @Description 交易dubbo接口
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findTradingByTradingState",retries = 2)
    })
public class TradingFaceImpl implements TradingFace {

    @Autowired
    ITradingService tradingService;

    @Override
    public List<TradingVo> findTradingByTradingState(String tradingState) {
        List<Trading>  tradings = tradingService.findTradingByTradingState(tradingState);
        return BeanConv.toBeanList(tradings,TradingVo.class);
    }
}
