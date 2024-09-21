package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.Store;
import com.itheima.restkeeper.pojo.Store;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.StoreVo;

import java.util.List;

/**
 * @Description：门店信息账号 服务类
 */
public interface IStoreService extends IService<Store> {

    /**
     * @Description 品牌列表
     * @param storeVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<BrandVo>
     */
    Page<Store> findStoreVoPage(StoreVo storeVo, int pageNum, int pageSize);

    /**
     * @Description 创建门店
     * @param storeVo 对象信息
     * @return Store
     */
    Store createStore(StoreVo storeVo);

    /**
     * @Description 修改门店
     * @param storeVo 对象信息
     * @return Boolean
     */
    Boolean updateStore(StoreVo storeVo);

    /**
     * @Description 删除门店
     * @param checkedIds 选择的门店ID
     * @return Boolean
     */
    Boolean deleteStore(String[] checkedIds);

    /***
     * @description 查询门店下拉框
     * @return: List<Store>
     */
    List<Store> findStoreVoList();

}
