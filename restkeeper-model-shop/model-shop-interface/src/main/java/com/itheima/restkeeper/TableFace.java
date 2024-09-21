package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TableVo;

import java.util.List;

/**
 * @Description 桌台dubbo接口服务
 */
public interface TableFace {

    /**
     * @param tableVo  查询条件
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return
     * @Description 桌台列表
     */
    Page<TableVo> findTableVoPage(TableVo tableVo,
                                  int pageNum,
                                  int pageSize)throws ProjectException;

    /**
     * @param tableVo 对象信息
     * @return TableVo
     * @Description 创建桌台
     */
    TableVo createTable(TableVo tableVo)throws ProjectException;

    /**
     * @param tableVo 对象信息
     * @return Boolean
     * @Description 修改桌台
     */
    Boolean updateTable(TableVo tableVo)throws ProjectException;

    /**
     * @param checkedIds 选择对象信息Id
     * @return Boolean
     * @Description 删除桌台
     */
    Boolean deleteTable(String[] checkedIds)throws ProjectException;


    /**
     * @param tableId 选择对象信息Id
     * @return TableVo
     * @Description 查找桌台
     */
    TableVo findTableByTableId(Long tableId)throws ProjectException;

    /***
     * @description 查询桌台下拉框
     * @return: List<TableVo>
     */
    List<TableVo> findTableVoList()throws ProjectException;
}
