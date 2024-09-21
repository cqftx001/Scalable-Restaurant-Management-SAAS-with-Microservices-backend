package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.mapper.RoleMapper;
import com.itheima.restkeeper.pojo.RoleResource;
import com.itheima.restkeeper.pojo.UserRole;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.service.IRoleResourceService;
import com.itheima.restkeeper.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.service.IUserRoleService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：用户角色表 服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    IRoleResourceService roleResourceService;

    @Autowired
    IUserRoleService userRoleService;

    @Override
    public Page<Role> findRoleVoPage(RoleVo roleVo, int pageNum, int pageSize) {
        Page<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(roleVo.getLabel())) {
            queryWrapper.lambda().likeRight(Role::getLabel,roleVo.getLabel());
        }
        if (!EmptyUtil.isNullOrEmpty(roleVo.getRoleName())) {
            queryWrapper.lambda().likeRight(Role::getRoleName,roleVo.getRoleName());
        }
        if (!EmptyUtil.isNullOrEmpty(roleVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Role::getEnableFlag,roleVo.getEnableFlag());
        }
        return page(page, queryWrapper);
    }

    @Override
    public Role createRole(RoleVo roleVo) {
        Role role = BeanConv.toBean(roleVo, Role.class);

        boolean flag = save(role);
        if (flag){
            List<RoleResource> list = Lists.newArrayList();
            List<String> listResourceId = Arrays.asList(roleVo.getHasResourceIds());
            listResourceId.forEach(n->{
                RoleResource roleResource = RoleResource.builder()
                        .resourceId(Long.valueOf(n))
                        .roleId(role.getId())
                        .build();
                list.add(roleResource);
            });
            flag = roleResourceService.saveBatch(list);
        }
        if (flag){
            return role;
        }
        return null;
    }

    /**
     * @Description 角色拥有的资源
     */
    private List<String> roleHasResourceId(Long rolerId){
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleResource::getRoleId,rolerId);
        List<RoleResource> list = roleResourceService.list(queryWrapper);
        ArrayList<String> listResourceIds = Lists.newArrayList();
        if (!EmptyUtil.isNullOrEmpty(list)){
            list.forEach(n->{
                listResourceIds.add(String.valueOf(n.getResourceId()));
            });
        }
        return listResourceIds;
    }

    @Override
    @Transactional
    public Boolean updateRole(RoleVo roleVo) {
        Boolean flag = true;
        Role role = BeanConv.toBean(roleVo, Role.class);
        flag = updateById(role);
        List<String> newResourceIds = Arrays.asList(roleVo.getHasResourceIds());
        List<String> oldResourceIds = roleHasResourceId(role.getId());
        if (!EmptyUtil.isNullOrEmpty(oldResourceIds)){
            List<Long> idsLong = new ArrayList<>();
            oldResourceIds.forEach(n->{
                idsLong.add(Long.valueOf(n));
            });
            QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleResource::getRoleId,role.getId());
            roleResourceService.remove(queryWrapper);
        }
        List<RoleResource> list = Lists.newArrayList();
        newResourceIds.forEach(n->{
            RoleResource roleResource = RoleResource.builder()
                    .resourceId(Long.valueOf(n))
                    .roleId(role.getId())
                    .build();
            list.add(roleResource);
        });
        roleResourceService.saveBatch(list);
        return flag;
    }

    @Override
    public Boolean deleteRole(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        List<Role> roles = listByIds(idsLong);

        boolean flag = removeByIds(idsLong);
        if (flag){
            QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(RoleResource::getRoleId,idsLong);
            roleResourceService.remove(queryWrapper);
            QueryWrapper<UserRole> queryWrapperUserRole = new QueryWrapper<>();
            queryWrapperUserRole.lambda().in(UserRole::getRoleId,idsLong);
            flag = userRoleService.remove(queryWrapperUserRole);
        }
        return flag;
    }

    @Override
    public List<Role> initRoleIdOptions() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Role::getEnableFlag,SuperConstant.YES);
        return list(queryWrapper);
    }
}
