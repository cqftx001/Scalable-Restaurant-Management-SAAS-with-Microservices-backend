package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.BrandVo;

import java.util.List;

/**
 * @ClassName BrandFace.java
 * @Description 品牌dubbo服务定义
 */
public interface BrandFace {

    /**
     * @Description 品牌列表
     * @param brandVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<BrandVo>
     */
    Page<BrandVo> findBrandVoPage(BrandVo brandVo,
                                  int pageNum,
                                  int pageSize)throws ProjectException;

    /**
     * @Description 创建品牌
     * @param brandVo 对象信息
     * @return BrandVo
     */
    BrandVo createBrand(BrandVo brandVo)throws ProjectException;

    /**
     * @Description 修改品牌
     * @param brandVo 对象信息
     * @return Boolean
     */
    Boolean updateBrand(BrandVo brandVo)throws ProjectException;

    /**
     * @Description 删除品牌
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deleteBrand(String[] checkedIds)throws ProjectException;

    /**
     * @Description 查找品牌
     * @param brandId 选择对象信息Id
     * @return BrandVo
     */
    BrandVo findBrandByBrandId(Long brandId)throws ProjectException;

    /***
     * @description 查询品牌下拉框
     * @return: List<BrandVo>
     */
    List<BrandVo> findBrandVoList()throws ProjectException;

}
