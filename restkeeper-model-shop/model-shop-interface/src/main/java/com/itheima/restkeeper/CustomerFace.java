package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.CustomerVo;

import java.util.List;

/**
 * @ClassName CustomerFacd.java
 * @Description 客户dubbo服务
 */
public interface CustomerFace {

    /**
     * @Description 用户列表
     * @param customerVo 查询条件
     * @return
     */
    Page<CustomerVo> findCustomerVoPage(CustomerVo customerVo,
                                        int pageNum,
                                        int pageSize)throws ProjectException;

    /**
     * @Description 创建用户
     * @param customerVo 对象信息
     * @return
     */
    CustomerVo createCustomer(CustomerVo customerVo)throws ProjectException;

    /**
     * @Description 修改用户
     * @param customerVo 对象信息
     * @return
     */
    Boolean updateCustomer(CustomerVo customerVo)throws ProjectException;

    /**
     * @Description 删除用户
     * @param checkedIds 选择对象信息Id
     * @return
     */
    Boolean deleteCustomer(String[] checkedIds)throws ProjectException;

    /**
     * @Description 查找用户
     * @param customerId 选择对象信息Id
     * @return
     */
    CustomerVo findCustomerByCustomerId(Long customerId)throws ProjectException;

    /**
     * @Description 查找用户list
     * @return
     */
    List<CustomerVo> findCustomerVoList()throws ProjectException;

    /***
     * @description 启用禁用用户
     * @return
     * @return: java.lang.Boolean
     */
    Boolean updateCustomerEnableFlag(CustomerVo customerVo)throws ProjectException;
}
