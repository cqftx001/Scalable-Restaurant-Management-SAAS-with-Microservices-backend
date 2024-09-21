package com.itheima.restkeeper.handler.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.BeforePayHandler;
import com.itheima.restkeeper.handler.NativePayHandler;
import com.itheima.restkeeper.handler.alipay.config.AlipayConfig;
import com.itheima.restkeeper.pojo.RefundRecord;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.IRefundRecordService;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName AliNativePayHandler.java
 * @Description 支付宝Native支付方式：商户生成二维码，用户扫描支付
 */
@Slf4j
@Component
public class AliNativePayHandler implements NativePayHandler {

    @Autowired
    AlipayConfig alipayConfig;

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
        //1.2、交易前置处理：幂等性处理，交易单号设置完成
        tradingVo = beforePayHandler.idempotentCreateDownLineTrading(tradingVo);
        //2、获得支付宝配置文件
        Config config = alipayConfig.queryConfig(tradingVo.getEnterpriseId());
        //3、容器如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(config)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //4、Factory使用配置
        Factory.setOptions(config);
        try {
            //5、调用支付宝API面对面支付
            AlipayTradePrecreateResponse precreateResponse = Factory
                .Payment
                .FaceToFace()
                .preCreate(tradingVo.getMemo(),
                    String.valueOf(tradingVo.getTradingOrderNo()),
                    String.valueOf(tradingVo.getTradingAmount()));
            //受理结果【只表示请求是否成功，而不是支付是否成功】
            boolean isSuccess = ResponseChecker.success(precreateResponse);
            //6.1、受理成功：修改交易单
            if (isSuccess){
                String subCode = precreateResponse.getSubCode();
                String subMsg = precreateResponse.getQrCode();
                tradingVo.setPlaceOrderCode(subCode);
                tradingVo.setPlaceOrderMsg(subMsg);
                tradingVo.setPlaceOrderJson(JSONObject.toJSONString(precreateResponse));
                tradingVo.setTradingState(TradingConstant.FKZ); // 设置交易单状态 DFK --> FKZ
                Trading trading = BeanConv.toBean(tradingVo, Trading.class);
                flag = tradingService.saveOrUpdate(trading);
                if (!flag){
                    throw new ProjectException(TradingEnum.SAVE_OR_UPDATE_FAIL);
                }
                return BeanConv.toBean(trading, TradingVo.class);
            //6.2受理失败：抛出异常
            }else {
                throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("支付宝统一下单创建失败：{}", ExceptionsUtil.getStackTraceAsString(e));
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
        //2、获得支付宝配置文件
        Config config = alipayConfig.queryConfig(tradingVo.getEnterpriseId());
        //3、容器如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(config)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //4、使用配置
        Factory.setOptions(config);
        try {
            //5、调用支付宝API：通用查询支付情况
            AlipayTradeQueryResponse queryResponse = Factory
                .Payment
                .Common()
                .query(String.valueOf(tradingVo.getTradingOrderNo()));
            boolean success = ResponseChecker.success(queryResponse);
            //6、响应成功，分析交易状态
            if (success){
                //交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）
                // TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
                // TRADE_SUCCESS（交易支付成功）
                // TRADE_FINISHED（交易结束，不可退款）
                String tradeStatus = queryResponse.getTradeStatus();
                //6.1、支付取消：TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
                if (TradingConstant.ALI_TRADE_CLOSED.equals(tradeStatus)){
                    tradingVo.setTradingState(TradingConstant.QXDD);
                //6.2、支付成功：TRADE_SUCCESS（交易支付成功）TRADE_FINISHED（交易结束，不可退款）
                }else if (TradingConstant.ALI_TRADE_SUCCESS.equals(tradeStatus)||
                    TradingConstant.ALI_TRADE_FINISHED.equals(tradeStatus)){
                    tradingVo.setTradingState(TradingConstant.YJS);
                //6.3、非最终状态不处理，当前交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）不处理
                }else {
                    flag = false;
                }
                //7、修改交易单状态
                if (flag){
                    tradingVo.setResultCode(queryResponse.getSubCode());
                    tradingVo.setResultMsg(queryResponse.getSubMsg());
                    tradingVo.setResultJson(JSONObject.toJSONString(queryResponse));
                    Trading trading = BeanConv.toBean(tradingVo, Trading.class);
                    tradingService.saveOrUpdate(trading);
                }
            }
        } catch (Exception e) {
            log.warn("查询支付宝统一下单失败：{}", ExceptionsUtil.getStackTraceAsString(e));
        }
        //8、返回结果
        Trading trading = tradingService.findTradByProductOrderNo(tradingVo.getProductOrderNo());
        return BeanConv.toBean(trading,TradingVo.class);
    }

    @Override
    public TradingVo refundDownLineTrading(TradingVo tradingVo) throws ProjectException {
        //1、生成退款请求编号
        String outRequestNo = String.valueOf(identifierGenerator.nextId(tradingVo));
        tradingVo.setOutRequestNo(outRequestNo);
        //2.1、退款前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeRefundDownLineTrading(tradingVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_QUERY_FAIL);
        }
        //2.2、退款前置处理：退款幂等性校验
        beforePayHandler.idempotentRefundDownLineTrading(tradingVo);
        //3、获得支付宝配置文件
        Config config = alipayConfig.queryConfig(tradingVo.getEnterpriseId());
        //4、容器如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(config)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //5.1、使用配置
        Factory.setOptions(config);
        //5.2、部分退款必传
        try {
            AlipayTradeRefundResponse refundResponse = Factory.Payment
                .Common()
                .optional("out_request_no", outRequestNo)
                .refund(String.valueOf(tradingVo.getTradingOrderNo()),
                        String.valueOf(tradingVo.getOperTionRefund()));
            boolean success = ResponseChecker.success(refundResponse);
            //6、指定此交易单为退款交易单
            tradingVo.setIsRefund(SuperConstant.YES);
            //7、保存交易单信息
            Trading trading = BeanConv.toBean(tradingVo, Trading.class);
            tradingService.updateById(trading);
            RefundRecord refundRecord = BeanConv.toBean(trading, RefundRecord.class);
            //8、保存退款单信息
            refundRecord.setId(null);
            refundRecord.setRefundNo(outRequestNo);//本次退款订单号
            refundRecord.setRefundAmount(tradingVo.getOperTionRefund());//本次退款金额
            refundRecord.setRefundCode(refundResponse.getSubCode());
            refundRecord.setRefundCode(refundResponse.getSubMsg());
            if (success){
                refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_SENDING);
            }else {
                refundRecord.setRefundStatus(TradingConstant.REFUND_STATUS_FAIL);;
            }
            refundRecordService.save(refundRecord);
            return tradingVo;
        } catch (Exception e) {
            log.error("支付宝统一下单退款失败：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.REFUND_FAIL);
        }
    }

    @Override
    public void queryRefundDownLineTrading(RefundRecordVo refundRecordVo) throws ProjectException {
        //1、退款前置处理：检测退款单参数
        Boolean flag = beforePayHandler.checkeQueryRefundDownLineTrading(refundRecordVo);
        if (!flag){
            throw new ProjectException(TradingEnum.NATIVE_QUERY_FAIL);
        }
        //2.1、获得支付宝配置文件
        Config config = alipayConfig.queryConfig(refundRecordVo.getEnterpriseId());
        //2.2、容器如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(config)){
            throw  new ProjectException(TradingEnum.CONFIG_EMPT);
        }
        //3、使用配置
        Factory.setOptions(config);
        try {
            AlipayTradeFastpayRefundQueryResponse refundQueryResponse =
                Factory.Payment.Common().queryRefund(
                String.valueOf(refundRecordVo.getTradingOrderNo()),
                        refundRecordVo.getRefundNo());
            boolean success = ResponseChecker.success(refundQueryResponse);
            if (success){
                //4、查询状态
                String refundStatus = refundQueryResponse.getRefundStatus();
                //5、退款成功修改退款记录
                if (TradingConstant.REFUND_SUCCESS.equals(refundStatus)){
                    refundRecordVo.setRefundStatus(TradingConstant.REFUND_STATUS_SUCCESS);
                    refundRecordVo.setRefundCode(refundQueryResponse.getSubCode());
                    refundRecordVo.setRefundMsg(refundQueryResponse.getSubMsg());
                    refundRecordService.updateById(BeanConv.toBean(refundRecordVo,RefundRecord.class));
                }
            }
        } catch (Exception e) {
            log.warn("查询支付宝统一下单退款失败：{}", ExceptionsUtil.getStackTraceAsString(e));
        }
    }
}
