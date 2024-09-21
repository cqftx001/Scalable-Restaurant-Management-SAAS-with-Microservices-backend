package com.itheima.restkeeper.handler.credit;

import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.BeforePayHandler;
import com.itheima.restkeeper.handler.CreditPayHandler;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.BeanConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName CreditPayHandler.java
 * @Description 信用支付方式：免单、挂账
 */
@Component("creditPayHandler")
public class CreditPayHandlerImpl implements CreditPayHandler {

    @Autowired
    BeforePayHandler beforePayHandler;

    @Autowired
    ITradingService tradingService;

    @Override
    public TradingVo createCreditMdTrading(TradingVo tradingVo) throws ProjectException {

        //1.1、交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeCreateDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
        }
        //1.2、交易前置处理：幂等性处理
        TradingVo tradingVoResult = beforePayHandler.idempotentCreateDownLineTrading(tradingVo);
        //2、指定订单状态
        tradingVo.setResultCode(TradingConstant.MD);
        tradingVo.setResultMsg(TradingConstant.MD);
        tradingVo.setTradingState(TradingConstant.MD);
        tradingVo.setIsRefund(SuperConstant.NO);
        Trading trading = BeanConv.toBean(tradingVo, Trading.class);
        flag = tradingService.saveOrUpdate(trading);
        if (!flag){
            throw new ProjectException(TradingEnum.SAVE_OR_UPDATE_FAIL);
        }
        return BeanConv.toBean(trading,TradingVo.class);
    }

    @Override
    public TradingVo createCreditGzTrading(TradingVo tradingVo) throws ProjectException {
        //1.1、交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeCreateCreditGzTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
        }
        //1.2、交易前置处理：幂等性处理
        TradingVo tradingVoResult = beforePayHandler.idempotentCreateDownLineTrading(tradingVo);
        //2、指定订单状态
        tradingVo.setResultCode(TradingConstant.GZ);
        tradingVo.setResultMsg(TradingConstant.GZ);
        tradingVo.setTradingState(TradingConstant.GZ);
        tradingVo.setIsRefund(SuperConstant.NO);
        Trading trading = BeanConv.toBean(tradingVo, Trading.class);
        flag = tradingService.saveOrUpdate(trading);
        if (!flag){
            throw new ProjectException(TradingEnum.SAVE_OR_UPDATE_FAIL);
        }
        return BeanConv.toBean(trading,TradingVo.class);
    }
}
