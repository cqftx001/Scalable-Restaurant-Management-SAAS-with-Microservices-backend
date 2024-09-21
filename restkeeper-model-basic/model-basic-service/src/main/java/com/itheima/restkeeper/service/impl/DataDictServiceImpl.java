package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.DataDict;
import com.itheima.restkeeper.mapper.DataDictMapper;
import com.itheima.restkeeper.req.DataDictVo;
import com.itheima.restkeeper.service.IDataDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：数据字典表 服务实现类
 */
@Service
public class DataDictServiceImpl extends ServiceImpl<DataDictMapper, DataDict> implements IDataDictService {

    @Override
    public Page<DataDict> findDataDictVoPage(DataDictVo dataDictVo, int pageNum, int pageSize) {
        Page<DataDict> page = new Page<>(pageNum, pageSize);
        QueryWrapper<DataDict> queryWrapper = new QueryWrapper<>();
        //多条件查询
        if (!EmptyUtil.isNullOrEmpty(dataDictVo.getParentKey())){
            queryWrapper.lambda().eq(DataDict::getParentKey,dataDictVo.getParentKey());
        }
        if (!EmptyUtil.isNullOrEmpty(dataDictVo.getDataKey())){
            queryWrapper.lambda().eq(DataDict::getDataKey,dataDictVo.getDataKey());
        }
        queryWrapper.lambda().orderByAsc(DataDict::getParentKey);
        //执行查询
        return page(page, queryWrapper);
    }

    @Override
    public Boolean updateByDataKey(List<String> dataKeys, String enableFlag) {
        UpdateWrapper<DataDict> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(DataDict::getDataKey,dataKeys);
        DataDict dataDict = new DataDict();
        dataDict.setEnableFlag(enableFlag);
        return update(dataDict, updateWrapper);
    }

    @Override
    public Boolean checkByDataKey(String dataKey) {
        QueryWrapper<DataDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataDict::getDataKey,dataKey);
        DataDict dataDict = getOne(queryWrapper);
        return !EmptyUtil.isNullOrEmpty(dataKey);
    }

    @Override
    public String findValueByDataKey(String dataKey) {
        QueryWrapper<DataDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataDict::getDataKey,dataKey).eq(DataDict::getEnableFlag, SuperConstant.YES);
        DataDict dataDict = getOne(queryWrapper);
        if (!EmptyUtil.isNullOrEmpty(dataDict)){
            return dataDict.getDataValue();
        }
        return null;
    }

    @Override
    public List<DataDict> findDataDictByParentKey(String parentKey) {
        QueryWrapper<DataDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataDict::getParentKey,parentKey).eq(DataDict::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }

    @Override
    public List<DataDict> findValueByDataKeys(List<String> dataKeys) {
        QueryWrapper<DataDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(DataDict::getDataKey,dataKeys).eq(DataDict::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }
}
