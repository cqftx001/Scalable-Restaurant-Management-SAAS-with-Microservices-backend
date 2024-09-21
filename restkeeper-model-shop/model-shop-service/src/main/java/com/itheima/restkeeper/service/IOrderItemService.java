package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.OrderItem;
import com.itheima.restkeeper.req.OrderItemVo;

import java.util.List;

/**
 * @Description： 服务类
 */
public interface IOrderItemService extends IService<OrderItem> {

    /***
     * @description 按照orderNo查询订单项目
     *
     * @param orderNo
     * @return
     */
    List<OrderItem> findOrderItemByOrderNo(Long orderNo);

    /***
     * @description 查询菜品对应的订单项
     * @param dishId 菜品ID
     * @param orderNo 订单号
     * @return
     */
    OrderItem findOrderItemByDishId(Long dishId, Long orderNo);

    /***
     * @description 增减购物车订单项的商品数量
     * @param step 增减步长，负数：减少，正数：增加
     * @param  dishId 菜品Id
     * @return
     */
    Boolean updateDishNum(Long step, Long dishId, Long orderNo);
}
