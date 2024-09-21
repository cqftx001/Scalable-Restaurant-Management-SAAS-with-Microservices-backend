package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.mapper.OrderItemMapper;
import com.itheima.restkeeper.pojo.OrderItem;
import com.itheima.restkeeper.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description： 服务实现类
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> findOrderItemByOrderNo(Long orderNo) {
        LambdaQueryWrapper<OrderItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderItem::getProductOrderNo,orderNo);
        return list(lambdaQueryWrapper);
    }

    @Override
    public OrderItem findOrderItemByDishId(Long dishId, Long orderNo) {
        LambdaQueryWrapper<OrderItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderItem::getDishId,dishId)
                .eq(OrderItem::getProductOrderNo,orderNo);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public Boolean updateDishNum(Long step,Long dishId,Long orderNo) {
        Integer rows = orderItemMapper.updateDishNum(step,dishId,orderNo);
        return  rows==1;
    }
}
