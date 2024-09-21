package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.restkeeper.basic.BasicPojo;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.ResourceMapper;
import com.itheima.restkeeper.mapper.RoleMapper;
import com.itheima.restkeeper.mapper.UserMapper;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.service.IUserAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserAdapterServiceImpl.java
 * @Description 后台登陆用户适配器接口实现
 */
@Service
public class UserAdapterServiceImpl implements IUserAdapterService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public User findUserByMobilAndEnterpriseId(String mobil, Long enterpriseId) {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getEnableFlag, SuperConstant.YES)
                .eq(User::getMobil, mobil)
                .eq(User::getEnterpriseId, enterpriseId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findUserByUsernameAndEnterpriseId(String username,Long enterpriseId) {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getEnableFlag, SuperConstant.YES)
                .eq(User::getUsername, username)
                .eq(User::getEnterpriseId, enterpriseId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Role> findRoleByUserId(Long userId) {
        List<Role> list = roleMapper.findRoleByUserId(userId,SuperConstant.YES);
        return list;
    }

    @Override
    public List<Resource> findResourceByUserId(Long userId) {
        return resourceMapper.findResourceByUserId(SuperConstant.YES,userId);
    }
}
