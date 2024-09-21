package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.Trading;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description：交易订单表 服务类
 */
public interface ITradingService extends IService<Trading> {

    /***
     * @description 按交易单号查询交易单
     * @param tradingOrderNo 交易单号
     * @return
     */
    Trading findTradByTradingOrderNo(Long tradingOrderNo);

    /***
     * @description 按订单单号查询交易单
     * @param productOrderNo 交易单号
     * @return
     */
    Trading findTradByProductOrderNo(Long productOrderNo);

    /***
     * @description 按交易状态查询交易单
     * @param tradingState
     * @return
     */
    List<Trading> findTradingByTradingState(String tradingState);
}
