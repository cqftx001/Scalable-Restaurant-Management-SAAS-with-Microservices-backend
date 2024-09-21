package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.TableArea;
import com.itheima.restkeeper.pojo.TableArea;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.TableAreaVo;

import java.util.List;

/**
 * @Description：桌台区域 服务类
 */
public interface ITableAreaService extends IService<TableArea> {

    /**
     * @Description 区域列表
     * @param tableAreaVo 查询条件
     * @param pageNum     当前页
     * @param pageSize    每页大小
     * @return Page<TableArea>
     */
    Page<TableArea> findTableAreaVoPage(TableAreaVo tableAreaVo, int pageNum, int pageSize);

    /**
     * @Description 创建区域
     * @param tableAreaVo 对象信息
     * @return TableArea
     */
    TableArea createTableArea(TableAreaVo tableAreaVo);

    /**
     * @Description 修改区域
     * @param tableAreaVo 对象信息
     * @return Boolean
     */
    Boolean updateTableArea(TableAreaVo tableAreaVo);

    /**
     * @Description 删除区域
     * @param checkedIds 选择的区域ID
     * @return Boolean
     */
    Boolean deleteTableArea(String[] checkedIds);

    /***
     * @description 查询区域下拉框
     * @return:  List<TableArea>
     */
    List<TableArea> findTableAreaVoList();

}
