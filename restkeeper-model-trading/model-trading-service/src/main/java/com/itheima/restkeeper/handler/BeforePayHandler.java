package com.itheima.restkeeper.handler;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;

/**
 * @ClassName IdempotentHandler.java
 * @Description 交易前置处理接口
 */

public interface BeforePayHandler {


    /***
     * @description CreateDownLineTrading交易幂等性
     * @param tradingVo 交易订单
     * @return
     */
    TradingVo idempotentCreateDownLineTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description CreateDownLineTrading交易单参数校验
     * @param tradingVo 交易订单
     * @return
     */
    Boolean checkeCreateDownLineTrading(TradingVo tradingVo);

    /***
     * @description createCreditGzTrading交易单参数校验
     * @param tradingVo 交易订单
     * @return
     */
    Boolean checkeCreateCreditGzTrading(TradingVo tradingVo);

    /***
     * @description QueryDownLineTrading交易单参数校验
     * @param tradingVo 交易订单
     * @return
     */
    Boolean checkeQueryDownLineTrading(TradingVo tradingVo);

    /***
     * @description RefundDownLineTrading退款交易幂等性
     * @param tradingVo 交易订单
     * @return
     */
    void idempotentRefundDownLineTrading(TradingVo tradingVo);

    /***
     * @description RefundDownLineTrading退款交易单参数校验
     * @param tradingVo 交易订单
     * @return
     */
    Boolean checkeRefundDownLineTrading(TradingVo tradingVo);


    /***
     * @description QueryRefundDownLineTrading交易单参数校验
     * @param refundRecordVo 退款记录
     * @return
     */
    Boolean checkeQueryRefundDownLineTrading(RefundRecordVo refundRecordVo);
}
