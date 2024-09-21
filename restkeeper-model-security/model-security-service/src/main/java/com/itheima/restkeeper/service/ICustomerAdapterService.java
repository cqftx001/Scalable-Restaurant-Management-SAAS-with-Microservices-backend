package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.Customer;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;

import java.util.List;

/**
 * @ClassName ICustomerAdapterService.java
 * @Description 客户查询适配
 */
public interface ICustomerAdapterService {

    /**
     * @Description 按客户手机查找客户
     * @param mobil 手机号码
     * @return
     */
    User findCustomerByMobilAndEnterpriseId(String mobil, Long enterpriseId);

    /**
     * @Description 按客户名查找用户
     * @param username 登录名
     * @return
     */
    User findCustomerByUsernameAndEnterpriseId(String username, Long enterpriseId);

    /**
     * @Description 查找客户所有角色
     * @param customerId 客户Id
     * @return
     */
    List<Role> findRoleByCustomerId(Long customerId);

    /**
     * @Description 查询客户有那些资源
     * @param customerId 客户Id
     * @return
     */
    List<Resource> findResourceByCustomerId(Long customerId);

}
