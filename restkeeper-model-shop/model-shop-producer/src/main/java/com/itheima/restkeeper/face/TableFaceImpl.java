package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.TableFace;
import com.itheima.restkeeper.enums.TableEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Table;
import com.itheima.restkeeper.req.TableVo;
import com.itheima.restkeeper.service.ITableService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description 桌台dubbo接口实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findTableVoPage",retries = 2),
        @Method(name = "createTable",retries = 0),
        @Method(name = "updateTable",retries = 0),
        @Method(name = "deleteTable",retries = 0)
    })
public class TableFaceImpl implements TableFace {

    @Autowired
    ITableService tableService;

    @Override
    public Page<TableVo> findTableVoPage(TableVo tableVo,
                                         int pageNum,
                                         int pageSize) throws ProjectException{
        try {
            //查询所有桌台
            Page<Table> page = tableService.findTableVoPage(tableVo, pageNum, pageSize);
            Page<TableVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Table> tableList = page.getRecords();
            List<TableVo> tableVoList = BeanConv.toBeanList(tableList,TableVo.class);
            pageVo.setRecords(tableVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询桌台列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.SELECT_TABLE_LIST_FAIL);
        }
    }

    @Override
    public TableVo createTable(TableVo tableVo) throws ProjectException{
        try {
            //添加桌台
            return BeanConv.toBean( tableService.createTable(tableVo), TableVo.class);
        } catch (Exception e) {
            log.error("保存桌台异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.CREATE_FAIL);
        }
    }

    @Override
    @GlobalTransactional
    public Boolean updateTable(TableVo tableVo) throws ProjectException{
        try {
            //修改桌台
            return tableService.updateTable(tableVo);
        } catch (Exception e) {
            log.error("保存桌台异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteTable(String[] checkedIds) throws ProjectException{
        try {
            //删除桌台
            return tableService.deleteTable(checkedIds);
        } catch (Exception e) {
            log.error("删除桌台异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.DELETE_FAIL);
        }
    }

    @Override
    public TableVo findTableByTableId(Long tableId)throws ProjectException {
        try {
            //按ID查询桌台
            Table table = tableService.getById(tableId);
            if (!EmptyUtil.isNullOrEmpty(table)){
                return BeanConv.toBean(table,TableVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找桌台所有桌台异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.SELECT_TABLE_FAIL);
        }
    }

    @Override
    public List<TableVo> findTableVoList()throws ProjectException {
        try {
            //查询桌台列表，用在哪里？是否要注意状态
            return BeanConv.toBeanList(tableService.findTableVoList(),TableVo.class);
        } catch (Exception e) {
            log.error("查找桌台所有桌台异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableEnum.SELECT_TABLE_LIST_FAIL);
        }
    }
}
