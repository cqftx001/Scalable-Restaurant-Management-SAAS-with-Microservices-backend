package com.itheima.restkeeper.adapter.impl;

import com.itheima.restkeeper.adapter.CreditPayAdapter;
import com.itheima.restkeeper.constant.TradingCacheConstant;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.CreditPayHandler;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CreditPayAdapterImpl.java
 * @Description 信用支付方式：免单、挂账
 */
@Slf4j
@Component("creditPayAdapter")
public class CreditPayAdapterImpl implements CreditPayAdapter {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    CreditPayHandler creditPayHandler;

    @Override
    public TradingVo createCreditMdTrading(TradingVo tradingVo) throws ProjectException {
        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key = TradingCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS);
            if (flag){
                //2、信用支付方式：免单
                return creditPayHandler.createCreditMdTrading(tradingVo);
            }else {
                throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("免单支付方式交易异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public TradingVo createCreditGzTrading(TradingVo tradingVo) throws ProjectException {
        //1、对交易订单加锁
        Long productOrderNo = tradingVo.getProductOrderNo();
        String key = TradingCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            boolean flag = lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS);
            if (flag){
                //2、信用支付方式：挂账
                return creditPayHandler.createCreditGzTrading(tradingVo);
            }else {
                throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("免单支付方式交易异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.CREDIT_PAY_FAIL);
        }finally {
            lock.unlock();
        }
    }
}
