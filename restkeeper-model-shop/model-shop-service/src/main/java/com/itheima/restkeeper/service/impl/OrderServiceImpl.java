package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.mapper.OrderMapper;
import com.itheima.restkeeper.pojo.Order;
import com.itheima.restkeeper.pojo.Table;
import com.itheima.restkeeper.req.OrderVo;
import com.itheima.restkeeper.service.IOrderService;
import com.itheima.restkeeper.service.ITableService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description： 服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    ITableService tableService;

    @Override
    public OrderVo findOrderByTableId(Long tableId) {
      /*  QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getTableId,tableId);
        queryWrapper.lambda().eq(Order::getEnableFlag,SuperConstant.YES);
        queryWrapper.lambda().and(wrapper->wrapper
                .eq(Order::getOrderState, TradingConstant.DFK)
                .or()
                .eq(Order::getOrderState,TradingConstant.FKZ));*/
        // s * from order where tid=1 and ef=yes and ( orderstate=DFK or  orderstate=fkz )
        LambdaQueryWrapper<Order> queryWrapper = Wrappers.<Order>lambdaQuery();
        queryWrapper.eq(Order::getTableId, tableId)
                .eq(Order::getEnableFlag,SuperConstant.YES)
                .and(wrapper ->
                        wrapper.eq(Order::getOrderState, TradingConstant.DFK)
                        .or()
                        .eq(Order::getOrderState, TradingConstant.FKZ)
                );
        Order order = getOne(queryWrapper);
        return BeanConv.toBean(order,OrderVo.class);
    }

    @Override
    public OrderVo findOrderByOrderNo(Long orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getOrderNo,orderNo);
        queryWrapper.lambda().eq(Order::getEnableFlag,SuperConstant.YES);
        queryWrapper.lambda().and(wrapper->wrapper
                .eq(Order::getOrderState,TradingConstant.DFK)
                .or().eq(Order::getOrderState,TradingConstant.FKZ));
        Order order = getOne(queryWrapper);
        return BeanConv.toBean(order,OrderVo.class);
    }

    @Override
    public Boolean rotaryTable(Long sourceTableId, Long targetTableId, Long orderNo) {
        //查询目标桌台
        Table table = tableService.getById(targetTableId);
        Order order = Order.builder()
                .tableId(table.getId())
                .tableName(table.getTableName())
                .areaId(table.getAreaId()).build();
        //订单修改
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getTableId,sourceTableId).eq(Order::getOrderNo,orderNo);
        lambdaQueryWrapper.eq(Order::getOrderState,TradingConstant.DFK);
        return update(order,lambdaQueryWrapper);
    }

    @Override
    public Page<Order> findOrderVoPage(OrderVo orderVo, int pageNum, int pageSize) {
        Page<Order> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(orderVo.getOrderNo())) {
            queryWrapper.lambda().eq(Order::getOrderNo,orderVo.getOrderNo());
        }
        if (!EmptyUtil.isNullOrEmpty(orderVo.getTableId())) {
            queryWrapper.lambda().eq(Order::getTableId,orderVo.getTableId());
        }
        if (!EmptyUtil.isNullOrEmpty(orderVo.getIsRefund())) {
            queryWrapper.lambda().eq(Order::getIsRefund,orderVo.getIsRefund());
        }
        if (!EmptyUtil.isNullOrEmpty(orderVo.getOrderState())) {
            queryWrapper.lambda().eq(Order::getOrderState,orderVo.getOrderState());
        }
        if (!EmptyUtil.isNullOrEmpty(orderVo.getTradingChannel())) {
            queryWrapper.lambda().eq(Order::getTradingChannel,orderVo.getTradingChannel());
        }
        queryWrapper.lambda().orderByDesc(Order::getCreatedTime);
        return page(page,queryWrapper);
    }

    @Override
    public OrderVo findOrderVoPaid(Long orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getOrderNo,orderNo);
        queryWrapper.lambda().eq(Order::getEnableFlag,SuperConstant.YES);
        queryWrapper.lambda().eq(Order::getOrderState,TradingConstant.YJS);
        Order order = getOne(queryWrapper);
        return BeanConv.toBean(order,OrderVo.class);
    }

    @Override
    public List<OrderVo> findOrderVoPaying() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getEnableFlag,SuperConstant.YES);
        queryWrapper.lambda().eq(Order::getOrderState,TradingConstant.FKZ);
        List<Order> orderList = list(queryWrapper);
        return BeanConv.toBeanList(orderList,OrderVo.class);
    }

    @Override
    public Boolean updateOrderStateByOrderNo(Long orderNo, String orderState) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Order::getOrderNo,orderNo);
        Order order = Order.builder().orderState(orderState).build();
        return update(order,updateWrapper);
    }

}
