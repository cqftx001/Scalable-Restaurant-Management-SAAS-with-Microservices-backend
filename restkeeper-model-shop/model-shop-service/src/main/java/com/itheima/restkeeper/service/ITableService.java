package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.Table;
import com.itheima.restkeeper.req.TableVo;

import java.util.List;

/**
 * @Description：桌台服务类
 */
public interface ITableService extends IService<Table> {

    /**
     * @param tableVo  查询条件
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return Page<Table>
     * @Description 门店列表
     */
    Page<Table> findTableVoPage(TableVo tableVo, int pageNum, int pageSize);

    /**
     * @param tableVo 对象信息
     * @return Table
     * @Description 创建门店
     */
    Table createTable(TableVo tableVo);

    /**
     * @param tableVo 对象信息
     * @return Boolean
     * @Description 修改门店
     */
    Boolean updateTable(TableVo tableVo);

    /**
     * @param checkedIds 选择的门店ID
     * @return Boolean
     * @Description 删除门店
     */
    Boolean deleteTable(String[] checkedIds);

    /***
     * @description 查询门店下拉框
     * @return: List<Table>
     */
    List<Table> findTableVoList();

    /***
     * @description 当桌台处于空闲状态，进行开桌
     * @param tableVo
     * @return
     */
    Boolean openTable(TableVo tableVo);
}
