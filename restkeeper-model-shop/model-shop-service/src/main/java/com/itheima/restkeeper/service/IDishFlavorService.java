package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.DishFlavor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description： 服务类
 */
public interface IDishFlavorService extends IService<DishFlavor> {

    /***
     * @description 查询菜品口味
     * @param dishId
     */
    List<DishFlavor> findDishFlavorByDishId(Long dishId);

}
