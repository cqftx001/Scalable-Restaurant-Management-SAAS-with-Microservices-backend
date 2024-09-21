package com.itheima.restkeeper;

import com.itheima.restkeeper.req.TradingVo;

import java.util.List;

/**
 * @ClassName TradingFace.java
 * @Description 交易dubbo接口
 */
public interface TradingFace {


    /***
     * @description 按交易状态查询交易单
     * @param tradingState
     * @return
     */
    List<TradingVo> findTradingByTradingState(String tradingState);
}
