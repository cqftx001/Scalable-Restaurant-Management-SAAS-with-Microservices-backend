package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description：角色资源表 服务类
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /***
     * @description 查询角色应用的资源IDs
     * @return
     * @return: java.util.List<java.lang.String>
     */
    List<String> findResourceIdByRoleId(Long roleId);

}
