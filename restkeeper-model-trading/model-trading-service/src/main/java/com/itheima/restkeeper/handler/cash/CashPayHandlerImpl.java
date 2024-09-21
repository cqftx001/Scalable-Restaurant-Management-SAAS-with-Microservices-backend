package com.itheima.restkeeper.handler.cash;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.BeforePayHandler;
import com.itheima.restkeeper.handler.CashPayHandler;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.IRefundRecordService;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.BeanConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName CashPayHandlerImpl.java
 * @Description 现金结算
 */
@Component("cashPayHandler")
public class CashPayHandlerImpl implements CashPayHandler {

    @Autowired
    BeforePayHandler beforePayHandler;

    @Autowired
    ITradingService tradingService;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Autowired
    IRefundRecordService refundRecordService;

    @Override
    public TradingVo createCachTrading(TradingVo tradingVo) throws ProjectException {
        //1.1、交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeCreateDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.CASH_PAY_FAIL);
        }
        //1.2、交易前置处理：幂等性处理
        TradingVo tradingVoResult = beforePayHandler.idempotentCreateDownLineTrading(tradingVo);
        //2、指定订单状态
        tradingVo.setResultCode(TradingConstant.YJS);
        tradingVo.setResultMsg(TradingConstant.YJS);
        tradingVo.setTradingState(TradingConstant.YJS);
        Trading trading = BeanConv.toBean(tradingVo, Trading.class);
        flag = tradingService.saveOrUpdate(trading);
        if (!flag){
            throw new ProjectException(TradingEnum.SAVE_OR_UPDATE_FAIL);
        }
        return BeanConv.toBean(trading,TradingVo.class);
    }

    @Override
    public TradingVo refundCachTrading(TradingVo tradingVo) {
        //1、生产退款请求编号
        String outRequestNo = String.valueOf(identifierGenerator.nextId(tradingVo));
        tradingVo.setOutRequestNo(outRequestNo);
        //2.1、退款前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeRefundDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_QUERY_FAIL);
        }
        //2.2、退款前置处理：退款幂等性校验
        beforePayHandler.idempotentRefundDownLineTrading(tradingVo);
        //3、指定此订单为退款交易单
        tradingVo.setIsRefund(SuperConstant.YES);
        //4、保存交易单信息
        Trading trading = BeanConv.toBean(tradingVo, Trading.class);
        tradingService.updateById(trading);
        RefundRecord refundRecord = BeanConv.toBean(trading, RefundRecord.class);
        //5、保存退款单信息
        refundRecord.setId(null);
        refundRecord.setRefundNo(outRequestNo);//本次退款订单号
        refundRecord.setRefundAmount(tradingVo.getOperTionRefund());//本次退款金额
        refundRecord.setRefundCode(TradingConstant.REFUND_STATUS_SUCCESS);
        refundRecord.setRefundCode(TradingConstant.REFUND_STATUS_SUCCESS);
        refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_SUCCESS);;
        refundRecordService.save(refundRecord);
        return tradingVo;
    }

}
