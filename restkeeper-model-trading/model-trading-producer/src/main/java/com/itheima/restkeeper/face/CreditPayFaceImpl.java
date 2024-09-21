package com.itheima.restkeeper.face;

import com.itheima.restkeeper.CreditPayFace;
import com.itheima.restkeeper.adapter.CashPayAdapter;
import com.itheima.restkeeper.adapter.CreditPayAdapter;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TradingVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName CreditPayFaceImpl.java
 * @Description 信用支付方式：免单、挂账
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "createCreditMdTrading",retries = 0),
        @Method(name = "createCreditGzTrading",retries = 0),
    })
public class CreditPayFaceImpl implements CreditPayFace {

    @Autowired
    CreditPayAdapter creditPayAdapter;

    @Override
    public TradingVo createCreditMdTrading(TradingVo tradingVo) throws ProjectException {
        return creditPayAdapter.createCreditMdTrading(tradingVo);
    }

    @Override
    public TradingVo createCreditGzTrading(TradingVo tradingVo) throws ProjectException {
        return creditPayAdapter.createCreditGzTrading(tradingVo);
    }
}
