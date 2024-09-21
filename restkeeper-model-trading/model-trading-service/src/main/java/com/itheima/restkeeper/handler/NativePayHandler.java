package com.itheima.restkeeper.handler;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;

/**
 * @ClassName NativePayHandler.java
 * @Description Native支付方式Handler：商户生成二维码，用户扫描支付
 */
public interface NativePayHandler {

    /***
     * @description 统一收单线下交易预创建
     * 收银员通过收银台或商户后台调用此接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * @param tradingVo 交易单
     * @return  交易单
     */
    TradingVo createDownLineTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description 统一收单线下交易查询
     * 该接口提供所有支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
     * @param tradingVo 交易单
     * @return
     */
    TradingVo queryDownLineTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description 统一收单交易退款接口
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * @param tradingVo 交易单
     * @return
     */
    TradingVo refundDownLineTrading(TradingVo tradingVo) throws ProjectException;

    /***
     * @description 统一收单交易退款查询接口
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * @param refundRecordVo 退款交易单
     * @return
     */
    void queryRefundDownLineTrading(RefundRecordVo refundRecordVo) throws ProjectException;
}
