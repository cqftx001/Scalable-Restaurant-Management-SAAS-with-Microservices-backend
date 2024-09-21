package com.itheima.restkeeper.adapter;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TradingVo;

/**
 * @ClassName CashPayAdapter.java
 * @Description 现金支付方式：商户收钱直接操作
 */
public interface CashPayAdapter {

    /***
     * @description 现金支付方式：商户收钱直接操作接口
     * @param tradingVo 交易单
     * @return  交易单
     */
    TradingVo createCachTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description 现金退款
     * @param tradingVo 交易单
     * @return  交易单
     */
    TradingVo refundCachTrading(TradingVo tradingVo);
}
