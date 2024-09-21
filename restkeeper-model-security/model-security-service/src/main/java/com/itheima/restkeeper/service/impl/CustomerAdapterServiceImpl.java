package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.CustomerMapper;
import com.itheima.restkeeper.mapper.ResourceMapper;
import com.itheima.restkeeper.mapper.RoleMapper;
import com.itheima.restkeeper.mapper.UserMapper;
import com.itheima.restkeeper.pojo.Customer;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.service.ICustomerAdapterService;
import com.itheima.restkeeper.utils.BeanConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CustomerAdapterServiceImpl.java
 * @Description 客户查询适配实现
 */
@Service
public class CustomerAdapterServiceImpl implements ICustomerAdapterService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public User findCustomerByMobilAndEnterpriseId(String mobil, Long enterpriseId) {
        QueryWrapper<Customer> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Customer::getEnableFlag, SuperConstant.YES)
                .eq(Customer::getMobil, mobil)
                .eq(Customer::getEnterpriseId, enterpriseId);
        Customer customer = customerMapper.selectOne(queryWrapper);
        return BeanConv.toBean(customer, User.class);
    }

    @Override
    public User findCustomerByUsernameAndEnterpriseId(String username, Long enterpriseId) {
        QueryWrapper<Customer> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Customer::getEnableFlag, SuperConstant.YES)
                .eq(Customer::getUsername, username)
                .eq(Customer::getEnterpriseId, enterpriseId);
        Customer customer = customerMapper.selectOne(queryWrapper);
        return BeanConv.toBean(customer, User.class);
    }

    @Override
    public List<Role> findRoleByCustomerId(Long customerId) {
        List<Role> list = roleMapper.findRoleByCustomerId(customerId,SuperConstant.YES);
        return list;
    }

    @Override
    public List<Resource> findResourceByCustomerId(Long customerId) {
        return resourceMapper.findResourceByCustomerId(SuperConstant.YES,customerId);
    }
}
