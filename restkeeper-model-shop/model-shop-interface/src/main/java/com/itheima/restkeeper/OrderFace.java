package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.OrderItemVo;
import com.itheima.restkeeper.req.OrderVo;
import com.itheima.restkeeper.req.TradingVo;

import java.util.List;

/**
 * @ClassName OrderFace.java
 * @Description 订单接口
 */
public interface OrderFace {

    /**
     * @Description 订单列表
     * @param dishVo 查询条件
     * @return
     */
    Page<OrderVo> findOrderVoPage(OrderVo dishVo,
                                  int pageNum,
                                  int pageSize)throws ProjectException;


    /***
     * @description 调整订单项菜品数量
     * @param dishId 菜品ID
     * @param orderNo 订单
     * @param opertionType 操作类型
     * @return
     */
    OrderVo opertionToOrderItem(Long dishId,
                                Long orderNo,
                                String opertionType) throws ProjectException;

    /***
     * @description 发起订单结算
     * @param orderVo 订单信息
     * @return: com.itheima.restkeeper.req.TradingVo
     */
    TradingVo handleTrading(OrderVo orderVo)throws ProjectException;


    /***
     * @description 查看二维码
     * @param orderVo 订单信息
     * @return: com.itheima.restkeeper.req.TradingVo
     */
    String queryQrCode(OrderVo orderVo);

    /***
     * @description 发起订单退款
     * @param orderVo 订单信息
     * @return: com.itheima.restkeeper.req.TradingVo
     */
    Boolean handleTradingRefund(OrderVo orderVo)throws ProjectException;

    /***
     * @description 发起免单
     * @param orderVo 订单信息
     * @return: com.itheima.restkeeper.req.TradingVo
     */
    Boolean handleTradingMd(OrderVo orderVo)throws ProjectException;

    /***
     * @description 发起挂账
     * @param orderVo 订单信息
     * @return: com.itheima.restkeeper.req.TradingVo
     */
    Boolean handleTradingGz(OrderVo orderVo);

    /***
     * @description 查询已支付订单
     * @param orderNo d订单编号
     * @return
     */
    OrderVo findOrderVoPaid(Long orderNo)throws ProjectException;

    /***
     * @description 查询FKZ的订单
     * @return
     */
    List<OrderVo> findOrderVoPaying()throws ProjectException;

    /***
     * @description 同步交易状态
     *
     * @param orderNo 订单号
     * @param tradingState 交易状态
     * @return
     */
    Boolean synchTradingState(Long orderNo,String tradingState)throws ProjectException;



}
