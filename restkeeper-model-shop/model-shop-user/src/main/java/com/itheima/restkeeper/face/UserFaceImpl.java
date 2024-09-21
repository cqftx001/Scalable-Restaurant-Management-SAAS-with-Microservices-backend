package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.UserFace;
import com.itheima.restkeeper.enums.UserEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.service.IUserAdapterService;
import com.itheima.restkeeper.service.IUserService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserFaceImpl.java
 * @Description 用户dubbo服务实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findUserVoPage",retries = 2),
        @Method(name = "createUser",retries = 0),
        @Method(name = "updateUser",retries = 0),
        @Method(name = "deleteUser",retries = 0)
    })
public class UserFaceImpl implements UserFace {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    AffixFace affixFace;

    @Autowired
    IUserService userService;

    @Autowired
    IUserAdapterService userAdapterService;

    @Override
    public Page<UserVo> findUserVoPage(UserVo userVo,
                                       int pageNum,
                                       int pageSize)throws ProjectException {
        try {
            Page<User> page = userService.findUserVoPage(userVo, pageNum, pageSize);
            Page<UserVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<User> userList = page.getRecords();
            List<UserVo> userVoList = BeanConv.toBeanList(userList,UserVo.class);
            if (!EmptyUtil.isNullOrEmpty(userList)){
                userVoList.forEach(n->{
                    //处理附件
                    List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(n.getId());
                    if (!EmptyUtil.isNullOrEmpty(affixVoList)){
                        n.setAffixVo(affixVoList.get(0));
                    }
                    //处理角色
                    List<Role> roles = userAdapterService.findRoleByUserId(n.getId());
                    List<String> roleIdList = new ArrayList<>();
                    for (Role role : roles) {
                        roleIdList.add(String.valueOf(role.getId()));
                    }
                    String[] roleIds = new String[roleIdList.size()];
                    roleIdList.toArray(roleIds);
                    n.setHasRoleIds(roleIds);
                });
            }
            pageVo.setRecords(userVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询用户列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.PAGE_FAIL);
        }
    }

    @Override
    public UserVo createUser(UserVo userVo)throws ProjectException {
        try {
            //保存用户
            UserVo userVoResult = BeanConv.toBean(userService.createUser(userVo), UserVo.class);
            //绑定附件
            if (!EmptyUtil.isNullOrEmpty(userVoResult)){
                affixFace.bindBusinessId(AffixVo.builder()
                    .businessId(userVoResult.getId())
                    .id(userVo.getAffixVo().getId())
                    .build());
            }
            userVoResult.setAffixVo(AffixVo.builder()
                .pathUrl(userVo.getAffixVo().getPathUrl())
                .businessId(userVoResult.getId())
                .id(userVo.getAffixVo().getId()).build());
            //处理角色
            userVoResult.setHasRoleIds(userVo.getHasRoleIds());
            return userVoResult;
        } catch (Exception e) {
            log.error("保存用户异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateUser(UserVo userVo) throws ProjectException{
        try {
            Boolean flag = userService.updateUser(userVo);
            if (flag){
                List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(userVo.getId());
                List<Long> affixIds = affixVoList.stream().map(AffixVo::getId).collect(Collectors.toList());
                if (!affixIds.contains(userVo.getAffixVo().getId())){
                    //删除图片
                    flag = affixFace.deleteAffixVoByBusinessId(userVo.getId());
                    //绑定新图片
                    affixFace.bindBusinessId(AffixVo.builder()
                        .businessId(userVo.getId())
                        .id(userVo.getAffixVo().getId())
                        .build());
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("保存用户异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteUser(String[] checkedIds)throws ProjectException {
        try {
            boolean flag =  userService.deleteUser(checkedIds);
            if (flag){
                //删除图片
                for (String checkedId : checkedIds) {
                    affixFace.deleteAffixVoByBusinessId(Long.valueOf(checkedId));
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("删除用户异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.DELETE_FAIL);
        }
    }

    @Override
    public UserVo findUserByUserId(Long userId)throws ProjectException {
        try {
            User user = userService.getById(userId);
            if (!EmptyUtil.isNullOrEmpty(user)){
                return BeanConv.toBean(user,UserVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找用户所有角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_USER_FAIL);
        }
    }

    @Override
    public List<UserVo> findUserVoList()throws ProjectException {
        try {
            return BeanConv.toBeanList(userService.findUserVoList(),UserVo.class);
        } catch (Exception e) {
            log.error("查找用户list异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_USER_LIST_FAIL);
        }
    }
}
