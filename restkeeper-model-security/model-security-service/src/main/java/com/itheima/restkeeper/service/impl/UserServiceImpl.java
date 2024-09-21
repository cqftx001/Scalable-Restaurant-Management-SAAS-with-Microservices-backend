package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.UserMapper;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.pojo.UserRole;
import com.itheima.restkeeper.req.UserVo;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IUserRoleService userRoleService;

    @Override
    public Page<User> findUserVoPage(UserVo userVo, int pageNum, int pageSize) {
        Page<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(userVo.getUsername())) {
            queryWrapper.lambda().likeRight(User::getUsername,userVo.getUsername());
        }
        if (!EmptyUtil.isNullOrEmpty(userVo.getMobil())) {
            queryWrapper.lambda().likeRight(User::getMobil,userVo.getMobil());
        }
        if (!EmptyUtil.isNullOrEmpty(userVo.getEnableFlag())) {
            queryWrapper.lambda().eq(User::getEnableFlag,userVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByDesc(User::getCreatedTime);
        return page(page, queryWrapper);
    }

    @Override
    public User createUser(UserVo userVo) {
        User user = BeanConv.toBean(userVo, User.class);
        boolean flag = save(user);
        if (flag){
            List<UserRole> list = Lists.newArrayList();
            List<String> listRoleId = Arrays.asList(userVo.getHasRoleIds());
            listRoleId.forEach(n->{
                UserRole userRole = UserRole.builder()
                        .roleId(Long.valueOf(n))
                        .userId(user.getId())
                        .build();
                list.add(userRole);
            });
            flag = userRoleService.saveBatch(list);
        }
        if (flag){
            return user;
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
    public Boolean updateUser(UserVo userVo) {
        User user = BeanConv.toBean(userVo, User.class);
        boolean flag = updateById(user);
        if (!flag){
            return flag;
        }
        List<String> newRoleIds = Arrays.asList(userVo.getHasRoleIds());
        List<String> oldRoleIds = this.userHasRoleId(userVo.getId());
        if (!EmptyUtil.isNullOrEmpty(oldRoleIds)){
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(UserRole::getUserId,user.getId());
            userRoleService.remove(queryWrapper);
        }
        List<UserRole> list = Lists.newArrayList();
        newRoleIds.forEach(n->{
            UserRole userRole = UserRole.builder()
                    .userId(user.getId())
                    .roleId(Long.valueOf(n))
                    .build();
            list.add(userRole);
        });
        userRoleService.saveBatch(list);
        return flag;
    }

    @Override
    public Boolean deleteUser(String[] checkedIds) {
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
    public List<User> findUserVoList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEnableFlag, SuperConstant.YES);
        return list(queryWrapper);
    }

    @Override
    public Boolean updateUserEnableFlag(UserVo userVo) {
        User user = BeanConv.toBean(userVo, User.class);
        return updateById(user);
    }
}
