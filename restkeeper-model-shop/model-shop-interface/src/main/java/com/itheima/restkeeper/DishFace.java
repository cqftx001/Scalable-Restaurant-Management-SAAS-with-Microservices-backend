package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.DishVo;

/**
 * @ClassName DishFace.java
 * @Description 菜品接口
 */
public interface DishFace {

    /**
     * @Description 菜品列表
     * @param dishVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @return Page<DishVo>
     */
    Page<DishVo> findDishVoPage(DishVo dishVo,
                                int pageNum,
                                int pageSize)throws ProjectException;

    /**
     * @Description 创建菜品
     * @param dishVo 对象信息
     * @return DishVo
     */
    DishVo createDish(DishVo dishVo)throws ProjectException;

    /**
     * @Description 修改菜品
     * @param dishVo 对象信息
     * @return Boolean
     */
    Boolean updateDish(DishVo dishVo) throws ProjectException;

    /**
     * @Description 删除菜品
     * @param checkedIds 选择对象信息Id
     * @return Boolean
     */
    Boolean deleteDish(String[] checkedIds)throws ProjectException;


    /**
     * @Description 查找菜品
     * @param dishId 选择对象信息Id
     * @return DishVo
     */
    DishVo findDishByDishId(Long dishId)throws ProjectException;

}
