package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.DataDictFace;
import com.itheima.restkeeper.constant.DictCacheConstant;
import com.itheima.restkeeper.enums.DataDictEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.DataDict;
import com.itheima.restkeeper.req.DataDictVo;
import com.itheima.restkeeper.service.IDataDictService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DataDictFaceImpl.java
 * @Description 数字字典接口实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findDataDictVoPage",retries = 2),
        @Method(name = "saveDataDict",retries = 0),
        @Method(name = "updateDataDict",retries = 0),
        @Method(name = "findDataDictVoById",retries = 2),
        @Method(name = "updateByDataKey",retries = 0),
        @Method(name = "checkByDataKey",retries = 2),
        @Method(name = "findValueByDataKey",retries = 2),
        @Method(name = "findValueByDataKeys",retries = 2),
        @Method(name = "findDataDictVoByParentKey",retries = 2)
    })
public class DataDictFaceImpl implements DataDictFace {

    @Autowired
    IDataDictService dataDictService;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Page<DataDictVo> findDataDictVoPage(DataDictVo dataDictVo,int pageNum, int pageSize)  throws ProjectException{
        try {
            Page<DataDict> page = dataDictService.findDataDictVoPage(dataDictVo, pageNum, pageSize);
            Page<DataDictVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<DataDict> routeList = page.getRecords();
            List<DataDictVo> routeVoList = BeanConv.toBeanList(routeList,DataDictVo.class);
            pageVo.setRecords(routeVoList);
            return pageVo;
        }catch (Exception e){
            log.error("查询数据字典列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.PAGE_FAIL);
        }
    }

    @Override
    public DataDictVo saveDataDict(DataDictVo dataDictVo) throws ProjectException{
        String dataKey = dataDictVo.getDataKey();
        //添加锁
        RLock lock = redissonClient.getLock(DictCacheConstant.LOCK_PREFIX + dataKey);
        DataDict dataDict =null;
        try {
            Boolean flag = false;
            if (lock.tryLock(
                    DictCacheConstant.REDIS_WAIT_TIME,
                    DictCacheConstant.REDIS_LEASETIME,
                    TimeUnit.SECONDS)){
                flag = this.checkByDataKey(dataKey);
                if (flag){
                    //保存或修改信息
                    dataDict =BeanConv.toBean(dataDictVo,DataDict.class);
                    dataDictService.save(dataDict);
                }
            }
        } catch (Exception e) {
            log.error("保存数字字典异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SAVE_FAIL);
        }finally {
            lock.unlock();
        }
        return BeanConv.toBean(dataDict,DataDictVo.class);
    }

    @Override
    public Boolean updateDataDict(DataDictVo dataDictVo) throws ProjectException{
        Boolean flag = false;
        String dataKey = dataDictVo.getDataKey();
        //添加锁
        RLock lock = redissonClient.getLock(DictCacheConstant.LOCK_PREFIX + dataKey);
        DataDict dataDict =null;
        try {
            if (lock.tryLock(
                    DictCacheConstant.REDIS_WAIT_TIME,
                    DictCacheConstant.REDIS_LEASETIME,
                    TimeUnit.SECONDS)){
                flag = this.checkByDataKey(dataKey);
                if (flag){
                    //保存或修改信息
                    dataDict =BeanConv.toBean(dataDictVo,DataDict.class);
                    flag = dataDictService.updateById(dataDict);
                }
            }
        } catch (InterruptedException ex) {
            log.warn("修改数字字典异常：{}", ExceptionsUtil.getStackTraceAsString(ex));
            throw new ProjectException(DataDictEnum.UPDATE_FAIL);
        }finally {
            lock.unlock();
        }
        return flag;
    }

    @Override
    public DataDictVo findDataDictVoById(String dataDictId) throws ProjectException{
        try {
            return BeanConv.toBean(dataDictService.getById(dataDictId), DataDictVo.class);
        }catch (Exception e){
            log.error("根ID查询数据字典：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SELECT_DATAKEY_FAIL);
        }
    }

    @Override
    public Boolean updateByDataKey(List<String> dataKeys,String enableFlag) throws ProjectException{
        try {
            return dataDictService.updateByDataKey(dataKeys,enableFlag);
        }catch (Exception e){
            log.error("根ID查询数据字典：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SELECT_DATAKEY_FAIL);
        }
    }

    @Override
    public Boolean checkByDataKey(String dataKey) throws ProjectException{
        return dataDictService.checkByDataKey(dataKey);
    }

    @Override
    public String findValueByDataKey(String dataKey) throws ProjectException{
        try {
            return dataDictService.findValueByDataKey(dataKey);
        }catch (Exception e){
            log.error("查询数据字典列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SELECT_DATAKEY_FAIL);
        }
    }

    @Override
    public List<DataDictVo> findValueByDataKeys(List<String> dataKeys) throws ProjectException{
        try {
            return BeanConv.toBeanList(dataDictService.findValueByDataKeys(dataKeys),DataDictVo.class);
        }catch (Exception e){
            log.error("查询数据字典列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SELECT_DATAKEY_FAIL);
        }
    }

    @Override
    public List<DataDictVo> findDataDictVoByParentKey(String parentKey) throws ProjectException{
        try {
            return BeanConv.toBeanList(dataDictService.findDataDictByParentKey(parentKey),DataDictVo.class);
        }catch (Exception e){
            log.error("查询数据字典列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DataDictEnum.SELECT_PARENTKEY_FAIL);
        }
    }
}
