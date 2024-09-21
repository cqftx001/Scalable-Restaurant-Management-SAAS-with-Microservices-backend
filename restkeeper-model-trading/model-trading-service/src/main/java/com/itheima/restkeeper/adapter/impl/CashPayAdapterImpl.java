package com.itheima.restkeeper.adapter.impl;

import com.itheima.restkeeper.adapter.CashPayAdapter;
import com.itheima.restkeeper.constant.TradingCacheConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.CashPayHandler;
import com.itheima.restkeeper.handler.NativePayHandler;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CashPayAdapterImpl.java
 * @Description 现金支付方式：商户收钱直接操作
 */
@Slf4j
@Component
public class CashPayAdapterImpl implements CashPayAdapter {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    CashPayHandler cashPayHandler;

    @Override
    public TradingVo createCachTrading(TradingVo tradingVo) throws ProjectException {

        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key = TradingCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS);
            if (flag){
                //2、现金支付方式：商户收钱直接操作接口
                return cashPayHandler.createCachTrading(tradingVo);
            }else {
                throw new ProjectException(TradingEnum.CASH_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("现金支付方式交易异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.CASH_PAY_FAIL);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public TradingVo refundCachTrading(TradingVo tradingVo) {
        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key =TradingCacheConstant.REFUND_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS);
            if (flag){
                //2、现金退款接口
                return cashPayHandler.refundCachTrading(tradingVo);
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

}
