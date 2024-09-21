package com.itheima.restkeeper.face;

import com.itheima.restkeeper.UserAdapterFace;
import com.itheima.restkeeper.enums.UserEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.pojo.User;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.service.IUserAdapterService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName UserAdapterFaceImpl.java
 * @Description 用户权限适配服务接口定义实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",retries = 2,timeout = 5000)
public class UserAdapterFaceImpl implements UserAdapterFace {

    @Autowired
    IUserAdapterService userAdapterService;

    @Override
    public UserVo findUserByUsernameAndEnterpriseId(String username,Long enterpriseId) throws ProjectException{
        try {
            User user = userAdapterService.findUserByUsernameAndEnterpriseId(username,enterpriseId);
            return BeanConv.toBean(user,UserVo.class);
        } catch (Exception e) {
            log.error("查找用户有角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_USER_FAIL);
        }
    }

    @Override
    public UserVo findUserByMobilAndEnterpriseId(String mobil,Long enterpriseId) throws ProjectException{
        try {
            User user = userAdapterService.findUserByMobilAndEnterpriseId(mobil,enterpriseId);
            return BeanConv.toBean(user,UserVo.class);
        } catch (Exception e) {
            log.error("查找用户有角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_USER_FAIL);
        }
    }

    @Override
    public List<RoleVo> findRoleByUserId(Long userId)throws ProjectException {
        try {
            List<Role> roles = userAdapterService.findRoleByUserId(userId);
            return BeanConv.toBeanList(roles,RoleVo.class);
        } catch (Exception e) {
            log.error("查找用户有角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_ROLE_FAIL);
        }

    }

    @Override
    public List<ResourceVo> findResourceByUserId(Long userId)throws ProjectException {
        try {
            List<Resource> resources = userAdapterService.findResourceByUserId(userId);
            return BeanConv.toBeanList(resources,ResourceVo.class);
        } catch (Exception e) {
            log.error("查询用户有资源异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SELECT_RESOURCE_FAIL);
        }
    }
}
