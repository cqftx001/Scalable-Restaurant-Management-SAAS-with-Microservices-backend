package com.itheima.restkeeper;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName AppletFace.java
 * @Description 小程序接口
 */
public interface AppletFace {

    /***
     * @description 是否开桌,已开台：进入继续点餐流程,未开台：进入开台流程
     * @param tableId 桌台Id
     * @return Boolean true已开桌  false未开桌
     */
    Boolean isOpen(Long tableId)throws ProjectException;

    /***
     * @description 查询桌台相关主体信息
     * @param tableId 桌台Id
     * @return AppletInfoVo
     */
    AppletInfoVo findAppletInfoVoByTableId(Long tableId)throws ProjectException;

    /***
     * @description 未开桌：选择人数创建订单
     * @param tableId 桌台Id
     * @param personNumbers 就餐人数
     * @return Boolean
     */
    OrderVo openTable(Long tableId, Integer personNumbers)throws ProjectException;

    /***
     * @description 已开桌：查询当前桌台订单信息【包括可核算订单项和购物车订单项】
     * @param tableId 桌台ID
     * @return
     */
    OrderVo showOrderVoforTable(Long tableId) throws ProjectException;

    /***
     * @description 处理当前订单中订单项
     * 从DB中查询当前订单可核算订单项
     * 从redis查询当前订单购物车订单项
     * @param orderVo 订单信息
     * @return
     */
    OrderVo handlerOrderVo(OrderVo orderVo)throws ProjectException;

    /***
     * @description 订单项计算
     * @param orderItemVos 需要计算的订单项
     * @return
     * @return: java.math.BigDecimal
     */
    BigDecimal reducePriceHandler(List<OrderItemVo> orderItemVos)throws ProjectException;


    /***
     * @description 查询菜品详情
     * @return
     * @return: com.itheima.restkeeper.req.DishVo
     */
    DishVo findDishVoById(Long dishId) throws ProjectException;

    /***
     * @description 每次点击添加或减少，则添加或减少购物车订单项，同时增减库存
     * 【1】加入购物车逻辑：
     * 首先进行库存判定，如果库存够，则减库存，新增购物车订单项，如果库存不够则给出相对应提示信息，且回滚库存
     * 【2】减少购物车逻辑：
     * 首先从购物车订单项中移除菜品信息，回滚库存
     * @param dishId 菜品ID
     * @param orderNo 订单Id
     * @param dishFlavor 口味
     * @param opertionType 操作方式：移除购物车：REMOVE，加入购物车：ADD
     * @return
     */
    OrderVo opertionShoppingCart(Long dishId,
                                 Long orderNo,
                                 String dishFlavor,
                                 String opertionType) throws ProjectException;

    /***
     * @description 下单操作：添加购物车订单项到可结算订单项
     * @param orderNo 订单编号
     * @return
     */
    OrderVo placeOrder(Long orderNo) throws ProjectException ;

    /***
     * @description 转台业务，满足下列条件才可以转台：
     *  1、oldTableId处于USE状态
     *  2、newTableId处于FREE状态
     *  3、orderNo处于未付款，支付中状态
     * @param sourceTableId 源桌台
     * @param targetTableId 目标桌台
     * @param orderNo 订单号
     * @return
     */
    Boolean rotaryTable(Long sourceTableId, Long targetTableId, Long orderNo)throws ProjectException ;

    /***
     * @description 清理购物车
     * @param orderNo 订单号
     * @return 是否清理成功
     */
    Boolean clearShoppingCart(Long orderNo) throws ProjectException;
}
