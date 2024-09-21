package com.itheima.restkeeper.face;

import com.itheima.restkeeper.CashPayFace;
import com.itheima.restkeeper.adapter.CashPayAdapter;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TradingVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName CashPayFaceImpl.java
 * @Description 现金支付方式：商户收钱直接操作
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "createCachTrading",retries = 0),
        @Method(name = "refundCachTrading",retries = 0)
    })
public class CashPayFaceImpl implements CashPayFace {

    @Autowired
    CashPayAdapter cashPayAdapter;

    @Override
    public TradingVo createCachTrading(TradingVo tradingVo) throws ProjectException {
        return cashPayAdapter.createCachTrading(tradingVo);
    }

    @Override
    public TradingVo refundCachTrading(TradingVo tradingVo) {
        return cashPayAdapter.refundCachTrading(tradingVo);
    }

}
