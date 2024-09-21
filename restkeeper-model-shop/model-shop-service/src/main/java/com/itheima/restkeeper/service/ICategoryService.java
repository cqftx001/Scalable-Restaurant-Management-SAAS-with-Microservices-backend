package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.Category;
import com.itheima.restkeeper.req.CategoryVo;

import java.util.List;

/**
 * @Description：菜品及套餐分类 服务类
 */
public interface ICategoryService extends IService<Category> {

    /**
     * @Description 分类列表
     * @param categoryVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 当前页
     * @return Page<Category>
     */
    Page<Category> findCategoryVoPage(CategoryVo categoryVo, int pageNum, int pageSize);

    /**
     * @Description 创建分类
     * @param categoryVo 对象信息
     * @return Category
     */
    Category createCategory(CategoryVo categoryVo);

    /**
     * @Description 修改分类
     * @param categoryVo 对象信息
     * @return Boolean
     */
    Boolean updateCategory(CategoryVo categoryVo);

    /**
     * @Description 删除分类
     * @param checkedIds 选择的分类ID
     * @return Boolean
     */
    Boolean deleteCategory(String[] checkedIds);

    /***
     * @description 查询分类下拉框
     * @return: List<Category>
     */
    List<Category> findCategoryVoList();

    /***
     * @description 查询当前门店的分类
     * @param storeId
     * @return List<Category>
     */
    List<Category> findCategoryVoByStoreId(Long storeId);
}
