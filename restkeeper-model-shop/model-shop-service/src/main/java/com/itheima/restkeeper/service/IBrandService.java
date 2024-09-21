package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.BrandVo;

import java.util.List;

/**
 * @Description：品牌管理 服务类
 */
public interface IBrandService extends IService<Brand> {

    /**
     * @Description 品牌列表
     * @param brandVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<BrandVo>
     */
    Page<Brand> findBrandVoPage(BrandVo brandVo, int pageNum, int pageSize);

    /**
     * @Description 创建品牌
     * @param brandVo 对象信息
     * @return
     */
    Brand createBrand(BrandVo brandVo);

    /**
     * @Description 修改品牌
     * @param brandVo 对象信息
     * @return Boolean
     */
    Boolean updateBrand(BrandVo brandVo);

    /**
     * @Description 删除品牌
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deleteBrand(String[] checkedIds);

    /***
     * @description 查询品牌下拉框
     * @return: List<BrandVo>
     */
    List<Brand> findBrandVoList();
}
