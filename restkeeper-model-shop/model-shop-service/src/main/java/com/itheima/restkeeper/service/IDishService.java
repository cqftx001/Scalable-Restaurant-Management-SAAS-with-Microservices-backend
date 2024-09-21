package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.Dish;
import com.itheima.restkeeper.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.DishVo;

import java.util.List;

/**
 * @Description：菜品管理 服务类
 */
public interface IDishService extends IService<Dish> {

    /**
     * @Description 菜品列表
     * @param dishVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @return Page<Dish>
     */
    Page<Dish> findDishVoPage(DishVo dishVo, int pageNum, int pageSize);

    /**
     * @Description 创建菜品
     * @param dishVo 对象信息
     * @return Dish
     */
    Dish createDish(DishVo dishVo);

    /**
     * @Description 修改菜品
     * @param dishVo 对象信息
     * @return Boolean
     */
    Boolean updateDish(DishVo dishVo);

    /**
     * @Description 删除菜品
     * @param checkedIds 选择的菜品ID
     * @return Boolean
     */
    Boolean deleteDish(String[] checkedIds);


    /***
     * @description 查询分类下所有菜品
     * @param categoryId
     * @return List<Dish>
     */
    List<Dish> findDishVoByCategoryId(Long categoryId);

    /***
     * @description 查询店铺下所有起售且有效菜品
     * @param storeId
     * @return List<Dish>
     */
    List<Dish> findDishVoByStoreId(Long storeId);


    /***
     * @description 增减菜品库存数量
     * @param step 增减步长
     * @return Boolean
     */
    Boolean updateDishNumber(Long step, Long dishId);

    /***
     * @description 查询所有有效的菜品信息
     * @return
     */
    List<Dish> findDishVos();
}
