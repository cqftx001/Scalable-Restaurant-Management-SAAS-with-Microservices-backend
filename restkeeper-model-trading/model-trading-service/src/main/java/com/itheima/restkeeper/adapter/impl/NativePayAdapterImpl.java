package com.itheima.restkeeper.adapter.impl;

import com.itheima.restkeeper.adapter.NativePayAdapter;
import com.itheima.restkeeper.constant.TradingCacheConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.NativePayHandler;
import com.itheima.restkeeper.pojo.Trading;
import com.itheima.restkeeper.req.OrderVo;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.service.ITradingService;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.RegisterBeanHandler;
import com.itheima.restkeeper.utils.ShowApiService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NativePayAdapterImpl.java
 * @Description Native支付方式适配实现：商户生成二维码，用户扫描支付
 */
@Slf4j
@Component
public class NativePayAdapterImpl implements NativePayAdapter {

    @Autowired
    ITradingService tradingService;

    @Autowired
    ShowApiService showApiService;

    @Autowired
    RegisterBeanHandler registerBeanHandler;

    @Autowired
    RedissonClient redissonClient;

    private static Map<String,String> nativePayHandlers =new HashMap<>();

    static {
        //Map key=支付渠道 value=IOC容器中支付实现类的名称
        nativePayHandlers.put(TradingConstant.TRADING_CHANNEL_ALI_PAY,"aliNativePayHandler");
        nativePayHandlers.put(TradingConstant.TRADING_CHANNEL_WECHAT_PAY,"wechatNativePayHandler");
    }

    @Override
    public String queryQrCodeUrl(OrderVo orderVo) throws ProjectException {
        Trading trading = tradingService.findTradByProductOrderNo(orderVo.getOrderNo());
        //之前生成过则直接返回
        if (!EmptyUtil.isNullOrEmpty(trading)&&!EmptyUtil.isNullOrEmpty(trading.getQrCodeUrl())){
            return trading.getQrCodeUrl();
        }
        //之前未生成直接调用万维易源生成二维码图片，并保存
        String qrCodeUrl = showApiService.handlerQRcode(trading.getPlaceOrderMsg());
        trading.setQrCodeUrl(qrCodeUrl);
        tradingService.updateById(trading);
        return qrCodeUrl;
    }

    @Override
    public TradingVo createDownLineTrading(TradingVo tradingVo) throws ProjectException {
        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key =TradingCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_LEASETIME, TimeUnit.SECONDS);
            if (flag){
                //2、从IOC容器中找到NativePayHandler实现
                String nativePayHandlerString = nativePayHandlers.get(tradingVo.getTradingChannel());
                NativePayHandler nativePayHandler = registerBeanHandler
                        .getBean(nativePayHandlerString, NativePayHandler.class);
                //3、Native支付交易处理 二维码地址
                TradingVo downLineTrading = nativePayHandler.createDownLineTrading(tradingVo);
                //4、构建支付二维码图片地址
                String qrCodeUrl = showApiService.handlerQRcode(downLineTrading.getPlaceOrderMsg());
                if (EmptyUtil.isNullOrEmpty(qrCodeUrl)) {
                    throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
                }
                downLineTrading.setQrCodeUrl(qrCodeUrl);
                return downLineTrading;
            }else {
                throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("统一收单线下交易预创建异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    @Override
    public TradingVo queryDownLineTrading(TradingVo tradingVo) throws ProjectException {
        String nativePayHandlerString = nativePayHandlers.get(tradingVo.getTradingChannel());
        NativePayHandler nativePayHandler = registerBeanHandler
                .getBean(nativePayHandlerString, NativePayHandler.class);
       return nativePayHandler.queryDownLineTrading(tradingVo);
    }

    @Override
    public TradingVo refundDownLineTrading(TradingVo tradingVo) throws ProjectException {
        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key =TradingCacheConstant.REFUND_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS);
            if (flag){
                //2、统一收单交易退款接口
                String nativePayHandlerString = nativePayHandlers.get(tradingVo.getTradingChannel());
                NativePayHandler nativePayHandler = registerBeanHandler
                        .getBean(nativePayHandlerString, NativePayHandler.class);
                return nativePayHandler.refundDownLineTrading(tradingVo);
            }else {
                throw new ProjectException(TradingEnum.NATIVE_REFUND_FAIL);
            }
        } catch (Exception e) {
            log.error("统一收单交易退款接口异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.NATIVE_REFUND_FAIL);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void queryRefundDownLineTrading(RefundRecordVo refundRecordVo) throws ProjectException {
        String nativePayHandlerString = nativePayHandlers.get(refundRecordVo.getTradingChannel());
        NativePayHandler nativePayHandler = registerBeanHandler
                .getBean(nativePayHandlerString, NativePayHandler.class);
        nativePayHandler.queryRefundDownLineTrading(refundRecordVo);
    }
}
