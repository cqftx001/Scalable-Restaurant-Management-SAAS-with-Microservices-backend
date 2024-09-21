package com.itheima.restkeeper;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.CustomerVo;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.req.UserVo;

import java.util.List;

/**
 * @Description：客户权限适配服务接口定义
 */
public interface CustomerAdapterFace {

    /**
     * @Description 按客户名查找客户
     * @param username 登录名
     * @return
     */
    UserVo findCustomerByUsernameAndEnterpriseId(String username, Long enterpriseId)throws ProjectException;

    /**
     * @Description 按客户手机查找客户
     * @param mobil 登录名
     * @return
     */
    UserVo findCustomerByMobilAndEnterpriseId(String mobil, Long enterpriseId) throws ProjectException;

    /**
     * @Description 查找客户所有角色
     * @param customerId 客户Id
     * @return
     */
    List<RoleVo> findRoleByCustomerId(Long customerId)throws ProjectException;

    /**
     * @Description 查询客户有资源
     * @param customerId 客户Id
     * @return
     */
    List<ResourceVo> findResourceByCustomerId(Long customerId)throws ProjectException;

}
