package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.DataDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.DataDictVo;

import java.util.List;

/**
 * @Description：数据字典表 服务类
 */
public interface IDataDictService extends IService<DataDict> {

    /***
     * @description 数据字典列表数据
     *
     * @param dataDictVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.itheima.restkeeper.req.DataDict>
     */
    Page<DataDict> findDataDictVoPage(DataDictVo dataDictVo, int pageNum, int pageSize);

    /**
     * @Description 禁用，启用数据字典
     * @return
     */
    Boolean updateByDataKey(List<String> dataKeys, String enableFlag);

    /**
     * @Description 检测key是否已经存在
     * @return
     */
    Boolean checkByDataKey(String dataKey);

    /**
     * @Description 根据dataKey获取value
     * @return
     */
    String findValueByDataKey(String dataKey);


    /**
     * @Description 获取父key下的数据
     * @return
     */
    List<DataDict> findDataDictByParentKey(String parentKey);

    /**
     * @Description 获取keys下的数据
     * @return
     */
    List<DataDict> findValueByDataKeys(List<String> dataKeys);
}
