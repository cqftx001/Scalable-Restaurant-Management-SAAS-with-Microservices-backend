package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.DataDictVo;

import java.util.List;

/**
 * @Description：
 */
public interface DataDictFace {

    /***
     * @description 数据字典列表数据
     *
     * @param dataDictVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return:  Page<DataDictVo>
     */
    Page<DataDictVo> findDataDictVoPage(DataDictVo dataDictVo, int pageNum, int pageSize) throws ProjectException;

    /**
     * @Description 保存字典数据
     * @return
     */
    DataDictVo saveDataDict(DataDictVo dataDictVo) throws ProjectException;

    /**
     * @Description 保存字典数据
     * @return
     */
    Boolean updateDataDict(DataDictVo dataDictVo) throws ProjectException;

    /**
     * @Description 根据主键查询数据字典对象
     * @return
     */
    DataDictVo findDataDictVoById(String dataDictId) throws ProjectException;

    /**
     * @Description 禁用，启用数据字典
     * @return
     */
    Boolean updateByDataKey(List<String> dataKeys, String enableFlag) throws ProjectException;

    /**
     * @Description 检测key是否已经存在
     * @return
     */
    Boolean checkByDataKey(String dataKey) throws ProjectException;

    /**
     * @Description 根据dataKey获取value
     * @return
     */
    String findValueByDataKey(String dataKey) throws ProjectException;

    /**
     * @Description 获取keys的数据
     * @return
     */
    List<DataDictVo> findValueByDataKeys(List<String> dataKeys) throws ProjectException;


    /**
     * @Description 获取父key下的数据
     * @return
     */
    List<DataDictVo> findDataDictVoByParentKey(String parentKey) throws ProjectException;

}
