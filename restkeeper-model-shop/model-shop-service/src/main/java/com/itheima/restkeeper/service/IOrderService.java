package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.Order;
import com.itheima.restkeeper.req.OrderVo;

import java.util.List;

/**
 * @Description： 服务类
 */
public interface IOrderService extends IService<Order> {

    /***
     * @description 查询当前处于待支付，付款中的有效订单
     * @param tableId
     * @return
     */
    OrderVo findOrderByTableId(Long tableId);

    /***
     * @description 查询当前处于待支付，付款中的有效订单
     * @param orderNo
     * @return
     */
    OrderVo findOrderByOrderNo(Long orderNo);

    /***
     * @description 转台操作
     * @param sourceTableId 源桌台
     * @param targetTableId 目标桌台
     * @param orderNo
     * @return
     */
    Boolean rotaryTable(Long sourceTableId, Long targetTableId, Long orderNo);

    /**
     * @Description 订单列表
     * @param orderVo 查询条件
     * @return
     */
    Page<Order> findOrderVoPage(OrderVo orderVo, int pageNum, int pageSize);


    /***
     * @description 查询已支付订单
     * @param orderNo 订单号
     * @return Order
     */
    OrderVo findOrderVoPaid(Long orderNo);

    /***
     * @description 查询付款中
     * @return
     */
    List<OrderVo> findOrderVoPaying();


    /***
     * @description 按订单编号修改订单状态
     *
     * @param orderNo
     * @param orderState
     * @return
     */
    Boolean updateOrderStateByOrderNo(Long orderNo, String orderState);
}
