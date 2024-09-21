package com.itheima.restkeeper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.restkeeper.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description：Mapper接口
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Update("update tab_order_item set dish_num=dish_num+#{step} where dish_num+#{step}>=0 and dish_id = #{dishId} and product_order_no = #{orderNo}")
    Integer updateDishNum(@Param("step") Long step, @Param("dishId") Long dishId, @Param("orderNo") Long orderNo);
}
