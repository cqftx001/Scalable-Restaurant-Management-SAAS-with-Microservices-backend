package com.itheima.restkeeper.handler.wechat;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.BeforePayHandler;
import com.itheima.restkeeper.handler.NativePayHandler;
import com.itheima.restkeeper.handler.alipay.config.AlipayConfig;
import com.itheima.restkeeper.handler.wechat.client.WechatPayClient;
import com.itheima.restkeeper.handler.wechat.config.WechatPayConfig;
import com.itheima.restkeeper.handler.wechat.response.PreCreateResponse;
import com.itheima.restkeeper.handler.wechat.response.QueryResponse;
import com.itheima.restkeeper.handler.wechat.response.RefundResponse;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.IRefundRecordService;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName WechatNativePayHandler.java
 * @Description Native支付方式：商户生成二维码，用户扫描支付
 */
@Component
public class WechatNativePayHandler implements NativePayHandler {

    @Autowired
    WechatPayConfig wechatPayConfig;

    @Autowired
    ITradingService tradingService;

    @Autowired
    IRefundRecordService refundRecordService;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Autowired
    BeforePayHandler beforePayHandler;

    @Override
    public TradingVo createDownLineTrading(TradingVo tradingVo) throws ProjectException {
        //1.1、交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeCreateDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
        }
        //1.2、交易前置处理：幂等性处理
        beforePayHandler.idempotentCreateDownLineTrading(tradingVo);
        //2、获得微信客户端
        WechatPayClient wechatPayClient = wechatPayConfig.queryConfig(tradingVo.getEnterpriseId());
        //3、客户端如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(wechatPayClient)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //4、调用微信API：preCreateResponse
        PreCreateResponse preCreateResponse = wechatPayClient.preCreate(
            String.valueOf(tradingVo.getTradingOrderNo()),
            String.valueOf(tradingVo.getTradingAmount()),
            tradingVo.getMemo());
        //5.1、受理成功：修改交易单
        if (!EmptyUtil.isNullOrEmpty(preCreateResponse)&&
            !EmptyUtil.isNullOrEmpty(preCreateResponse.getCodeUrl())){
            String subCode = preCreateResponse.getCode();
            String subMsg = preCreateResponse.getCodeUrl();
            //4.1、指定统一下单code
            tradingVo.setPlaceOrderCode(subCode);
            //4.2、指定统一下单返回信息
            tradingVo.setPlaceOrderMsg(subMsg);
            //4.3、指定统一下单json字符串
            tradingVo.setPlaceOrderJson(JSONObject.toJSONString(preCreateResponse));
            //4.4、指定交易状态
            tradingVo.setTradingState(TradingConstant.FKZ);
            //4.5、重新保存信息
            Trading trading = BeanConv.toBean(tradingVo, Trading.class);
            flag = tradingService.saveOrUpdate(trading);
            if (!flag){
                throw new ProjectException(TradingEnum.SAVE_OR_UPDATE_FAIL);
            }
            return BeanConv.toBean(trading, TradingVo.class);
        //5.2受理失败：抛出异常
        }else {
            throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
        }
    }

    @Override
    public TradingVo queryDownLineTrading(TradingVo tradingVo) throws ProjectException {
        //1、查询前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeQueryDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_QUERY_FAIL);
        }
        //2、获得微信客户端
        WechatPayClient wechatPayClient = wechatPayConfig.queryConfig(tradingVo.getEnterpriseId());
        //3、客户端如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(wechatPayClient)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //4、调用微信API：query
        QueryResponse queryResponse = wechatPayClient.query(String.valueOf(tradingVo.getTradingOrderNo()));
        //5、响应成功，分析交易状态
        if (!EmptyUtil.isNullOrEmpty(queryResponse)){
            //SUCCESS：支付成功
            //REFUND：转入退款
            //NOTPAY：未支付
            //CLOSED：已关闭
            //REVOKED：已撤销（仅付款码支付会返回）
            //USERPAYING：用户支付中（仅付款码支付会返回）
            //PAYERROR：支付失败（仅付款码支付会返回）
            String tradeStatus = queryResponse.getTradeState();
            //6.1、支付取消
            if (TradingConstant.WECHAT_TRADE_CLOSED.equals(tradeStatus)||
                TradingConstant.WECHAT_TRADE_REVOKED.equals(tradeStatus)){
                tradingVo.setTradingState(TradingConstant.QXDD);
            //6.2、支付成功
            }else if (TradingConstant.WECHAT_REFUND_SUCCESS.equals(tradeStatus)||
                    TradingConstant.WECHAT_TRADE_REFUND.equals(tradeStatus)){
                tradingVo.setTradingState(TradingConstant.YJS);
            //6.3、其他状态未非最终状态不处理
            }else {
                flag = false;
            }
            //7、修改交易单状态
            if (flag){
                tradingVo.setResultCode(queryResponse.getTradeState());
                tradingVo.setResultMsg(queryResponse.getTradeStateDesc());
                tradingVo.setResultJson(JSONObject.toJSONString(queryResponse));
                Trading trading = BeanConv.toBean(tradingVo, Trading.class);
                tradingService.saveOrUpdate(trading);
            }
        }
        //8、返回结果
        Trading trading = tradingService.findTradByProductOrderNo(tradingVo.getProductOrderNo());
        return BeanConv.toBean(trading,TradingVo.class);
    }

    @Override
    public TradingVo refundDownLineTrading(TradingVo tradingVo) throws ProjectException {
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
        //3、获得微信客户端
        WechatPayClient wechatPayClient = wechatPayConfig.queryConfig(tradingVo.getEnterpriseId());
        //4、客户端如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(wechatPayClient)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //5、调用微信API：refund
        RefundResponse refundResponse = wechatPayClient.refund(
                String.valueOf(tradingVo.getTradingOrderNo()),
                String.valueOf(tradingVo.getOperTionRefund()),
                outRequestNo,String.valueOf(tradingVo.getTradingAmount()));
        //6、指定此订单为退款交易单
        tradingVo.setIsRefund(SuperConstant.YES);
        //7、保存交易单信息
        Trading trading = BeanConv.toBean(tradingVo, Trading.class);
        tradingService.updateById(trading);
        RefundRecord refundRecord = BeanConv.toBean(trading, RefundRecord.class);
        //8、保存退款单信息
        refundRecord.setId(null);
        refundRecord.setRefundNo(outRequestNo);//本次退款订单号
        refundRecord.setRefundAmount(tradingVo.getOperTionRefund());//本次退款金额
        refundRecord.setRefundCode(refundResponse.getCode());
        if (!EmptyUtil.isNullOrEmpty(refundResponse)&&
                TradingConstant.WECHAT_REFUND_PROCESSING.equals(refundResponse.getStatus())){
            refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_SENDING);
        }else if (!EmptyUtil.isNullOrEmpty(refundResponse)&&
                TradingConstant.WECHAT_REFUND_SUCCESS.equals(refundResponse.getStatus())){
            refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_SUCCESS);
        }else {
            refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_FAIL);;
        }
        refundRecordService.save(refundRecord);
        return tradingVo;
    }

    @Override
    public void queryRefundDownLineTrading(RefundRecordVo refundRecordVo) throws ProjectException {
        //1、退款前置处理：检测退款单参数
        Boolean flag = beforePayHandler.checkeQueryRefundDownLineTrading(refundRecordVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_QUERY_FAIL);
        }
        //2.1、获得微信客户端
        WechatPayClient wechatPayClient = wechatPayConfig.queryConfig(refundRecordVo.getEnterpriseId());
        //2.2、客户端如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(wechatPayClient)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //3、调用微信API：queryRefund
        RefundResponse refundResponse = wechatPayClient.queryRefund(refundRecordVo.getRefundNo());
        //4、查询状态
        String refundStatus = refundResponse.getStatus();
        //5、退款成功修改退款记录
        if (TradingConstant.WECHAT_REFUND_SUCCESS.equals(refundStatus)){
            refundRecordVo.setRefundStatus(TradingConstant.REFUND_STATUS_SUCCESS);
            refundRecordService.updateById(BeanConv.toBean(refundRecordVo,RefundRecord.class));
        }
    }
}
