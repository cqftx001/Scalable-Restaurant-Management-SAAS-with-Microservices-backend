package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.restkeeper.pojo.RoleResource;
import com.itheima.restkeeper.mapper.RoleResourceMapper;
import com.itheima.restkeeper.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：角色资源表 服务实现类
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Override
    public List<String> findResourceIdByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(RoleResource::getResourceId)
                .eq(RoleResource::getRoleId,roleId);
        List<RoleResource> list = list(lambdaQueryWrapper);
        List<String> resourceIds = new ArrayList<>();
        if (!EmptyUtil.isNullOrEmpty(list)){
            list.forEach(n->{
                resourceIds.add(String.valueOf(n.getResourceId()));
            });
        }
        return resourceIds;
    }
}
