package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.CategoryVo;

import java.util.List;

/**
 * @ClassName CategoryFace.java
 * @Description 菜品分类dubbo服务接口
 */
public interface CategoryFace {

    /**
     * @Description 分类列表
     * @param categoryVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @return Page<CategoryVo>
     */
    Page<CategoryVo> findCategoryVoPage(CategoryVo categoryVo,
                                        int pageNum,
                                        int pageSize)throws ProjectException;

    /**
     * @Description 创建分类
     * @param categoryVo 对象信息
     * @return CategoryVo
     */
    CategoryVo createCategory(CategoryVo categoryVo)throws ProjectException;

    /**
     * @Description 修改分类
     * @param categoryVo 对象信息
     * @return Boolean
     */
    Boolean updateCategory(CategoryVo categoryVo)throws ProjectException;

    /**
     * @Description 删除分类
     * @param checkedIds 选择对象信息Id
     * @return Boolean
     */
    Boolean deleteCategory(String[] checkedIds)throws ProjectException;


    /**
     * @Description 查找分类
     * @param categoryId 选择对象信息Id
     * @return CategoryVo
     */
    CategoryVo findCategoryByCategoryId(Long categoryId)throws ProjectException;

    /***
     * @description 查询分类下拉框
     * @return: List<CategoryVo>
     */
    List<CategoryVo> findCategoryVoList()throws ProjectException;
}
