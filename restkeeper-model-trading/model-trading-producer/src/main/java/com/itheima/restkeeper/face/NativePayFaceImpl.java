package com.itheima.restkeeper.face;

import com.itheima.restkeeper.NativePayFace;
import com.itheima.restkeeper.adapter.NativePayAdapter;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.OrderVo;
import com.itheima.restkeeper.req.RefundRecordVo;
import com.itheima.restkeeper.req.TradingVo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName NativePayFaceImpl.java
 * @Description Native支付方式dubbo接口
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "queryQrCodeUrl",retries = 2),
        @Method(name = "createDownLineTrading",retries = 0),
        @Method(name = "queryDownLineTrading",retries = 2),
        @Method(name = "refundDownLineTrading",retries = 0),
        @Method(name = "queryRefundDownLineTrading",retries = 2),

    })
public class NativePayFaceImpl implements NativePayFace {

    @Autowired
    NativePayAdapter nativePayAdapter;

    @Override
    public String queryQrCodeUrl(OrderVo orderVo) throws ProjectException {
        return nativePayAdapter.queryQrCodeUrl(orderVo);
    }

    @Override
    @GlobalTransactional
    public TradingVo createDownLineTrading(TradingVo tradingVo) throws ProjectException {
        return nativePayAdapter.createDownLineTrading(tradingVo);
    }

    @Override
    @GlobalTransactional
    public TradingVo queryDownLineTrading(TradingVo tradingVo) throws ProjectException {
        return nativePayAdapter.queryDownLineTrading(tradingVo);
    }

    @Override
    @GlobalTransactional
    public TradingVo refundDownLineTrading(TradingVo tradingVo) throws ProjectException {
        return nativePayAdapter.refundDownLineTrading(tradingVo);
    }

    @Override
    @GlobalTransactional
    public void queryRefundDownLineTrading(RefundRecordVo refundRecordVo) throws ProjectException {
        nativePayAdapter.queryRefundDownLineTrading(refundRecordVo);
    }
}
