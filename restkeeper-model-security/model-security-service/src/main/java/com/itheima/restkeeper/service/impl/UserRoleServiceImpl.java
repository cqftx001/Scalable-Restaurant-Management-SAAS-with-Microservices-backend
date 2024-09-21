package com.itheima.restkeeper.service.impl;

import com.itheima.restkeeper.pojo.UserRole;
import com.itheima.restkeeper.mapper.UserRoleMapper;
import com.itheima.restkeeper.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description：用户角色表 服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
