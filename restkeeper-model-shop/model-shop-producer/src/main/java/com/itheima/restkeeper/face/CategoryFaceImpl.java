package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.CategoryFace;
import com.itheima.restkeeper.enums.CategoryEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Category;
import com.itheima.restkeeper.req.CategoryVo;
import com.itheima.restkeeper.service.ICategoryService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName CategoryFaceImpl.java
 * @Description 菜品分类dubbo服务接口实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findCategoryVoPage",retries = 2),
        @Method(name = "createCategory",retries = 0),
        @Method(name = "updateCategory",retries = 0),
        @Method(name = "deleteCategory",retries = 0)
    })
public class CategoryFaceImpl implements CategoryFace {

    @Autowired
    ICategoryService categoryService;


    @Override
    public Page<CategoryVo> findCategoryVoPage(CategoryVo categoryVo,
                                               int pageNum,
                                               int pageSize)throws ProjectException {
        try {
            //查询分类分页
            Page<Category> page = categoryService.findCategoryVoPage(categoryVo, pageNum, pageSize);
            Page<CategoryVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Category> categoryList = page.getRecords();
            List<CategoryVo> categoryVoList = BeanConv.toBeanList(categoryList,CategoryVo.class);
            pageVo.setRecords(categoryVoList);
            //返回结果
            return pageVo;
        } catch (Exception e) {
            log.error("查询分类列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.PAGE_FAIL);
        }

    }

    @Override
    public CategoryVo createCategory(CategoryVo categoryVo)throws ProjectException {
        try {
            //创建分类
            return BeanConv.toBean( categoryService.createCategory(categoryVo), CategoryVo.class);
        } catch (Exception e) {
            log.error("保存分类异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateCategory(CategoryVo categoryVo) throws ProjectException{
        try {
            //修改分类
            return categoryService.updateCategory(categoryVo);
        } catch (Exception e) {
            log.error("保存分类异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteCategory(String[] checkedIds) {
        try {
            //删除分类
            return categoryService.deleteCategory(checkedIds);
        } catch (Exception e) {
            log.error("删除分类异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.DELETE_FAIL);
        }
    }

    @Override
    public CategoryVo findCategoryByCategoryId(Long categoryId)throws ProjectException {
        try {
            //按分类id查询分类
            Category category = categoryService.getById(categoryId);
            if (!EmptyUtil.isNullOrEmpty(category)){
                return BeanConv.toBean(category,CategoryVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找分类所有分类异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.SELECT_CATEGORY_FAIL);
        }
    }

    @Override
    public List<CategoryVo> findCategoryVoList()throws ProjectException {
        try {
            //查询分类下拉框
            return BeanConv.toBeanList(categoryService.findCategoryVoList(),CategoryVo.class);
        } catch (Exception e) {
            log.error("查找分类所有分类异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CategoryEnum.SELECT_CATEGORY_LIST_FAIL);
        }
    }
}
