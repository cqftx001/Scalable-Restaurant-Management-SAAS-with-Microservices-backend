package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.CustomerMapper;
import com.itheima.restkeeper.mapper.UserMapper;
import com.itheima.restkeeper.pojo.Customer;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.pojo.UserRole;
import com.itheima.restkeeper.req.CustomerVo;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.service.ICustomerService;
import com.itheima.restkeeper.service.IUserRoleService;
import com.itheima.restkeeper.service.IUserService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：用户表 服务实现类
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    IUserRoleService userRoleService;

    @Override
    public Page<Customer> findCustomerVoPage(CustomerVo customerVo, int pageNum, int pageSize) {
        Page<Customer> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(customerVo.getUsername())) {
            queryWrapper.lambda().likeRight(Customer::getUsername,customerVo.getUsername());
        }
        if (!EmptyUtil.isNullOrEmpty(customerVo.getMobil())) {
            queryWrapper.lambda().eq(Customer::getMobil,customerVo.getMobil());
        }
        if (!EmptyUtil.isNullOrEmpty(customerVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Customer::getEnableFlag,customerVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByDesc(Customer::getCreatedTime);
        return page(page,queryWrapper);
    }

    @Override
    public Customer createCustomer(CustomerVo customerVo) {
        Customer customer = BeanConv.toBean(customerVo, Customer.class);
        boolean flag = save(customer);
        if (flag){
            List<UserRole> list = Lists.newArrayList();
            List<String> listRoleId = Arrays.asList(customerVo.getHasRoleIds());
            listRoleId.forEach(n->{
                UserRole userRole = UserRole.builder()
                        .roleId(Long.valueOf(n))
                        .userId(customer.getId())
                        .build();
                list.add(userRole);
            });
            flag = userRoleService.saveBatch(list);
        }
        if (flag){
            return customer;
        }
        return null;
    }

    /**
     * @Description 用户拥有的角色ID
     */
    private List<String> userHasRoleId(Long userId){
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserRole::getUserId,userId);
        List<UserRole> list = userRoleService.list(queryWrapper);
        ArrayList<String> listResourceIds = Lists.newArrayList();
        if (!EmptyUtil.isNullOrEmpty(list)){
            list.forEach(n->{
                listResourceIds.add(String.valueOf(n.getRoleId()));
            });
        }
        return listResourceIds;
    }

    @Override
    public Boolean updateCustomer(CustomerVo customerVo) {
        Customer customer = BeanConv.toBean(customerVo, Customer.class);
        boolean flag = updateById(customer);
        if (!flag){
            return flag;
        }
        List<String> newRoleIds = Arrays.asList(customerVo.getHasRoleIds());
        List<String> oldRoleIds = this.userHasRoleId(customerVo.getId());
        if (!EmptyUtil.isNullOrEmpty(oldRoleIds)){
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(UserRole::getUserId,customer.getId());
            userRoleService.remove(queryWrapper);
        }
        List<UserRole> list = Lists.newArrayList();
        newRoleIds.forEach(n->{
            UserRole userRole = UserRole.builder()
                    .userId(customer.getId())
                    .roleId(Long.valueOf(n))
                    .build();
            list.add(userRole);
        });
        flag =userRoleService.saveBatch(list);
        return flag;
    }

    @Override
    public Boolean deleteCustomer(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        boolean flag = removeByIds(idsLong);
        if (flag){
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(UserRole::getUserId,idsLong);
            flag = userRoleService.remove(queryWrapper);
        }
        return flag;
    }

    @Override
    public List<Customer> findCustomerVoList() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Customer::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }

    @Override
    public Boolean updateCustomerEnableFlag(CustomerVo customerVo) {
        Customer customer = BeanConv.toBean(customerVo, Customer.class);
        return updateById(customer);
    }
}
