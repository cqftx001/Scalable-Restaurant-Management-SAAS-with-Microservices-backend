package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.RoleFace;
import com.itheima.restkeeper.enums.RoleEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.service.IRoleResourceService;
import com.itheima.restkeeper.service.IRoleService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName RoleFaceImpl.java
 * @Description 角色服务实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
        methods ={
                @Method(name = "findRoleVoPage",retries = 2),
                @Method(name = "createRole",retries = 0),
                @Method(name = "updateRole",retries = 0),
                @Method(name = "deleteRole",retries = 0),
                @Method(name = "initRoleIdOptions",retries = 2)
        })
public class RoleFaceImpl implements RoleFace {

    @Autowired
    IRoleService roleService;

    @Autowired
    IRoleResourceService roleResourceService;

    @Override
    public Page<RoleVo> findRoleVoPage(RoleVo roleVo,
                                       int pageNum,
                                       int pageSize) throws ProjectException{
        try {
            Page<Role> page = roleService.findRoleVoPage(roleVo, pageNum, pageSize);
            Page<RoleVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Role> roleList = page.getRecords();
            List<RoleVo> roleVoList = BeanConv.toBeanList(roleList,RoleVo.class);
            if (!EmptyUtil.isNullOrEmpty(roleVoList)){
                roleVoList.forEach(n->{
                    List<String> resourceIdList = roleResourceService.findResourceIdByRoleId(n.getId());
                    String[] resourceIds = new String[resourceIdList.size()];
                    resourceIdList.toArray(resourceIds);
                    n.setHasResourceIds(resourceIds);
                });
            }
            pageVo.setRecords(roleVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询角色列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.PAGE_FAIL);
        }

    }

    @Override
    public RoleVo createRole(RoleVo roleVo) throws ProjectException{
        try {
            return BeanConv.toBean(roleService.createRole(roleVo),RoleVo.class);
        } catch (Exception e) {
            log.error("保存角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.CREATE_FAIL);
        }

    }

    @Override
    public Boolean updateRole(RoleVo roleVo) throws ProjectException{
        try {
            return roleService.updateRole(roleVo);
        } catch (Exception e) {
            log.error("修改角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteRole(String[] checkedIds)throws ProjectException {
        try {
            return roleService.deleteRole(checkedIds);
        } catch (Exception e) {
            log.error("删除角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<RoleVo> initRoleIdOptions()throws ProjectException {
        try {
            List<Role> roles = roleService.initRoleIdOptions();
            return BeanConv.toBeanList(roles,RoleVo.class);
        } catch (Exception e) {
            log.error("删除角色异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.INIT_ROLEID_OPTIONS_FAIL);
        }
    }
}
