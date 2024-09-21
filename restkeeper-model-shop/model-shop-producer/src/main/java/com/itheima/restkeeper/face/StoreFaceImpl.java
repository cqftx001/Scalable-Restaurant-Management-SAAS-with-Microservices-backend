package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.StoreFace;
import com.itheima.restkeeper.enums.StoreEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Store;
import com.itheima.restkeeper.req.StoreVo;
import com.itheima.restkeeper.service.IStoreService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName StoreFaceImpl.java
 * @Description 门店dubbbo服务实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findStoreVoPage",retries = 2),
        @Method(name = "createStore",retries = 0),
        @Method(name = "updateStore",retries = 0),
        @Method(name = "deleteStore",retries = 0)
    })
public class StoreFaceImpl implements StoreFace {

    @Autowired
    IStoreService storeService;

    @Override
    public Page<StoreVo> findStoreVoPage(StoreVo storeVo,
                                         int pageNum,
                                         int pageSize) throws ProjectException{
        try {
            //构建分页对象
            Page<Store> page = storeService.findStoreVoPage(storeVo, pageNum, pageSize);
            Page<StoreVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Store> storeList = page.getRecords();
            List<StoreVo> storeVoList = BeanConv.toBeanList(storeList,StoreVo.class);
            pageVo.setRecords(storeVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询门店列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.PAGE_FAIL);
        }
    }

    @Override
    public StoreVo createStore(StoreVo storeVo) throws ProjectException{
        try {
            return BeanConv.toBean( storeService.createStore(storeVo), StoreVo.class);
        } catch (Exception e) {
            log.error("保存门店异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateStore(StoreVo storeVo)throws ProjectException {
        try {
            return storeService.updateStore(storeVo);
        } catch (Exception e) {
            log.error("保存门店异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteStore(String[] checkedIds) throws ProjectException{
        try {
            return storeService.deleteStore(checkedIds);
        } catch (Exception e) {
            log.error("删除门店异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.DELETE_FAIL);
        }
    }

    @Override
    public StoreVo findStoreByStoreId(Long storeId)throws ProjectException {
        try {
            Store store = storeService.getById(storeId);
            if (!EmptyUtil.isNullOrEmpty(store)){
                return BeanConv.toBean(store,StoreVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找门店所有门店异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.SELECT_STORE_FAIL);
        }

    }

    @Override
    public List<StoreVo> findStoreVoList()throws ProjectException {
        try {
            return BeanConv.toBeanList(storeService.findStoreVoList(),StoreVo.class);
        } catch (Exception e) {
            log.error("查找门店所有门店异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(StoreEnum.SELECT_STORE_LIST_FAIL);
        }

    }
}
