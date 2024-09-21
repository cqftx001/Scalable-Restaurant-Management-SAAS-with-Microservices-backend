package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.TableAreaFace;
import com.itheima.restkeeper.enums.TableAreaEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.TableArea;
import com.itheima.restkeeper.req.TableAreaVo;
import com.itheima.restkeeper.service.ITableAreaService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName TableAreaFaceImpl.java
 * @Description 桌台区域dubbo服务
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findTableAreaVoPage",retries = 2),
        @Method(name = "createTableArea",retries = 0),
        @Method(name = "updateTableArea",retries = 0),
        @Method(name = "deleteTableArea",retries = 0)
    })
public class TableAreaFaceImpl implements TableAreaFace {

    @Autowired
    ITableAreaService tableAreaService;

    @Override
    public Page<TableAreaVo> findTableAreaVoPage(TableAreaVo tableAreaVo,
                                                 int pageNum,
                                                 int pageSize)throws ProjectException {
        try {
            //查询区域分页
            Page<TableArea> page = tableAreaService.findTableAreaVoPage(tableAreaVo, pageNum, pageSize);
            Page<TableAreaVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<TableArea> tableAreaList = page.getRecords();
            List<TableAreaVo> tableAreaVoList = BeanConv.toBeanList(tableAreaList,TableAreaVo.class);
            pageVo.setRecords(tableAreaVoList);
            //返回结果
            return pageVo;
        } catch (Exception e) {
            log.error("查询区域列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.PAGE_FAIL);
        }

    }

    @Override
    public TableAreaVo createTableArea(TableAreaVo tableAreaVo) throws ProjectException{
        try {
            //创建区域
            return BeanConv.toBean( tableAreaService.createTableArea(tableAreaVo), TableAreaVo.class);
        } catch (Exception e) {
            log.error("保存区域异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateTableArea(TableAreaVo tableAreaVo) throws ProjectException{
        try {
            //修改区域
            return tableAreaService.updateTableArea(tableAreaVo);
        } catch (Exception e) {
            log.error("保存区域异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteTableArea(String[] checkedIds)throws ProjectException {
        try {
            //删除区域
            return tableAreaService.deleteTableArea(checkedIds);
        } catch (Exception e) {
            log.error("删除区域异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.DELETE_FAIL);
        }
    }

    @Override
    public TableAreaVo findTableAreaByTableAreaId(Long tableAreaId)throws ProjectException {
        try {
            //按id查询区域
            TableArea tableArea = tableAreaService.getById(tableAreaId);
            if (!EmptyUtil.isNullOrEmpty(tableArea)){
                return BeanConv.toBean(tableArea,TableAreaVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找区域所有区域异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.SELECT_AREA_FAIL);
        }
    }

    @Override
    public List<TableAreaVo> findTableAreaVoList()throws ProjectException {
        try {
            //查询区域
            return BeanConv.toBeanList(tableAreaService.findTableAreaVoList(),TableAreaVo.class);
        } catch (Exception e) {
            log.error("查找区域所有区域异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TableAreaEnum.SELECT_AREA_LIST_FAIL);
        }
    }
}
