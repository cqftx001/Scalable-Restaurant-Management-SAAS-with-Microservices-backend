package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.RoleVo;

import java.util.List;

/**
 * @Description：角色服务接口
 */
public interface RoleFace {

    /**
     * @Description 角色列表
     * @param roleVo 查询条件
     * @return
     */
    Page<RoleVo> findRoleVoPage(RoleVo roleVo,
                                int pageNum,
                                int pageSize)throws ProjectException;

    /**
     * @Description 创建角色
     * @param roleVo 对象信息
     * @return
     */
    RoleVo createRole(RoleVo roleVo)throws ProjectException;

    /**
     * @Description 修改角色
     * @param roleVo 对象信息
     * @return
     */
    Boolean updateRole(RoleVo roleVo)throws ProjectException;

    /**
     * @Description 删除角色
     * @param checkedIds 选择IDS
     * @return
     */
    Boolean deleteRole(String[] checkedIds)throws ProjectException;

    /**
     * @Description 角色下拉框
     * @return
     */
    List<RoleVo> initRoleIdOptions()throws ProjectException;
}
