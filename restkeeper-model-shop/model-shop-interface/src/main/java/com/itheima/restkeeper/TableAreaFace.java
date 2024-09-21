package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TableAreaVo;

import java.util.List;

/**
 * @ClassName TableAreaFace.java
 * @Description 桌台区域dubbo服务
 */
public interface TableAreaFace {

    /**
     * @param tableAreaVo 查询条件
     * @param pageNum     当前页
     * @param pageSize    每页大小
     * @return Page<TableAreaVo>
     * @Description 区域列表
     */
    Page<TableAreaVo> findTableAreaVoPage(TableAreaVo tableAreaVo,
                                          int pageNum,
                                          int pageSize)throws ProjectException;

    /**
     * @param tableAreaVo 对象信息
     * @return TableAreaVo
     * @Description 创建区域
     */
    TableAreaVo createTableArea(TableAreaVo tableAreaVo)throws ProjectException;

    /**
     * @param tableAreaVo 对象信息
     * @return Boolean
     * @Description 修改区域
     */
    Boolean updateTableArea(TableAreaVo tableAreaVo)throws ProjectException;

    /**
     * @param checkedIds 选择对象信息Id
     * @return Boolean
     * @Description 删除区域
     */
    Boolean deleteTableArea(String[] checkedIds)throws ProjectException;


    /**
     * @param tableAreaId 选择对象信息Id
     * @return TableAreaVo
     * @Description 查找区域
     */
    TableAreaVo findTableAreaByTableAreaId(Long tableAreaId)throws ProjectException;

    /***
     * @description 查询区域下拉框
     * @return: List<TableAreaVo>
     */
    List<TableAreaVo> findTableAreaVoList()throws ProjectException;
}
