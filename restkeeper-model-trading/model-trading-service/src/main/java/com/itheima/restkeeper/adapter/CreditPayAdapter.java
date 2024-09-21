package com.itheima.restkeeper.adapter;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TradingVo;

/**
 * @ClassName CreditPayAdapter.java
 * @Description 信用支付方式：免单、挂账
 */
public interface CreditPayAdapter {

    /***
     * @description 免单：商户对用户免单
     * @param tradingVo 交易单
     * @return  交易单
     */
    TradingVo createCreditMdTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description 挂账：用户挂账
     * @param tradingVo 交易单
     * @return  交易单
     */
    TradingVo createCreditGzTrading(TradingVo tradingVo) throws ProjectException;

}
