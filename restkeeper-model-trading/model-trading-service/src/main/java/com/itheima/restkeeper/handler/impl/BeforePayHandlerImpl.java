package com.itheima.restkeeper.handler.impl;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.BeforePayHandler;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.IRefundRecordService;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName BeforePayHandlerImpl.java
 * @Description 阿里交易前置处理接口实现
 */
@Component("beforePayHandler")
public class BeforePayHandlerImpl implements BeforePayHandler {

    @Autowired
    ITradingService tradingService;

    @Autowired
    IRefundRecordService refundRecordService;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Override
    public TradingVo idempotentCreateDownLineTrading(TradingVo tradingVo) throws ProjectException {
        Trading trading = tradingService.findTradByProductOrderNo(tradingVo.getProductOrderNo());
        if (!EmptyUtil.isNullOrEmpty(trading)) {
            String tradingState = trading.getTradingState();
            //已结算、免单：直接抛出重复支付异常，解决重复支付问题
            if (TradingConstant.YJS.equals(tradingState) ||
                TradingConstant.MD.equals(tradingState)) {
                throw new ProjectException(TradingEnum.TRADING_STATE_SUCCEED);
             //付款中：直接抛出支付中异常
            } else if (TradingConstant.FKZ.equals(tradingState)) {
                throw new ProjectException(TradingEnum.TRADING_STATE_PAYING);
            //取消订单,挂账：创建交易号，对原交易单发起支付
            } else if (TradingConstant.QXDD.equals(tradingState)
                    ||TradingConstant.GZ.equals(tradingState)) {
                tradingVo.setId(trading.getId());
                tradingVo.setTradingOrderNo((Long)identifierGenerator.nextId(tradingVo));
            //其他情况：直接交易失败
            } else {
                throw new ProjectException(TradingEnum.PAYING_TRADING_FAIL);
            }
        }else {
            //首次支付
            tradingVo.setTradingOrderNo((Long)identifierGenerator.nextId(tradingVo));
        }
        return tradingVo;
    }

    @Override
    public Boolean checkeCreateDownLineTrading(TradingVo tradingVo) {
        Boolean flag =null;
        //订单为空
        if (EmptyUtil.isNullOrEmpty(tradingVo)) {
            flag = false;
        //订单号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getProductOrderNo())){
            flag = false;
        //企业号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getEnterpriseId())){
            flag = false;
        //交易金额为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getTradingAmount())){
            flag = false;
        //支付渠道为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getTradingChannel())){
            flag=false;
        }else {
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean checkeCreateCreditGzTrading(TradingVo tradingVo) {
        Boolean flag =null;
        //订单为空
        if (EmptyUtil.isNullOrEmpty(tradingVo)) {
            flag = false;
            //订单号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getProductOrderNo())){
            flag = false;
            //企业号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getEnterpriseId())){
            flag = false;
            //交易金额为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getTradingAmount())){
            flag = false;
            //支付渠道为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getTradingChannel())){
            flag=false;
            //挂账人为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getPayerId())||
                EmptyUtil.isNullOrEmpty(tradingVo.getPayerName())){
            flag=false;
        }
        else {
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean checkeQueryDownLineTrading(TradingVo tradingVo) {
        Boolean flag =null;
        //订单为空
        if (EmptyUtil.isNullOrEmpty(tradingVo)) {
            flag = false;
         //交易号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getTradingOrderNo())){
            flag = false;
        }else {
            flag = true;
        }
        return flag;
    }

    @Override
    public void idempotentRefundDownLineTrading(TradingVo tradingVo) {
        //查询交易单是否为以结算订单
        Trading trading = tradingService.findTradByProductOrderNo(tradingVo.getProductOrderNo());
        //交易单不存在，或者不为已结算状态：抛出退款失败异常
        if (EmptyUtil.isNullOrEmpty(trading)|| !TradingConstant.YJS.equals(trading.getTradingState())){
            throw new ProjectException(TradingEnum.NATIVE_REFUND_FAIL);
        }else {
            tradingVo.setTradingOrderNo(trading.getTradingOrderNo());
            tradingVo.setId(trading.getId());
            tradingVo.setTradingAmount(trading.getTradingAmount());
        }
        //查询是否有退款中的退款记录
        RefundRecord refundRecord = refundRecordService
                .findRefundRecordByProductOrderNoAndSending(tradingVo.getProductOrderNo());
        if (!EmptyUtil.isNullOrEmpty(refundRecord)){
            throw new ProjectException(TradingEnum.NATIVE_REFUND_FAIL);
        }
    }

    @Override
    public Boolean checkeRefundDownLineTrading(TradingVo tradingVo) {

        Boolean flag =null;
        //订单为空
        if (EmptyUtil.isNullOrEmpty(tradingVo)) {
            flag = false;
         //交易号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getProductOrderNo())){
            flag = false;
         //退款请求号为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getOutRequestNo())){
            flag = false;
        //当前退款金额为空
        }else if (EmptyUtil.isNullOrEmpty(tradingVo.getOperTionRefund())){
            flag = false;
        //退款总金额不可超实付总金额
        }else if (tradingVo.getRefund().compareTo(tradingVo.getTradingAmount()) > 0){
            flag = false;
        }else {
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean checkeQueryRefundDownLineTrading(RefundRecordVo refundRecordVo) {
        Boolean flag =null;
        //订单为空
        if (EmptyUtil.isNullOrEmpty(refundRecordVo)) {
            flag = false;
         //交易号为空
        }else if (EmptyUtil.isNullOrEmpty(refundRecordVo.getTradingOrderNo())){
            flag = false;
         //退款请求号为空
        }else if (EmptyUtil.isNullOrEmpty(refundRecordVo.getRefundNo())){
            flag = false;
            //退款金额为空
        }else {
            flag = true;
        }
        return flag;
    }
}
