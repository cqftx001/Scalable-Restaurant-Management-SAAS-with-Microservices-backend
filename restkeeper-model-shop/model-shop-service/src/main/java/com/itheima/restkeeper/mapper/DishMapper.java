package com.itheima.restkeeper.mapper;

import com.itheima.restkeeper.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description：菜品管理Mapper接口
 */
public interface DishMapper extends BaseMapper<Dish> {


    @Update("update tab_dish set dish_number = dish_number + #{step} where dish_number + #{step}>=0 and id = #{dishId}")
    Integer updateDishNumber(@Param("step") Long step, @Param("dishId") Long dishId);
}
