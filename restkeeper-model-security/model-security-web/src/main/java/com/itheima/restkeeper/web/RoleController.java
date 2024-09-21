package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.RoleFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.RoleEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController.java
 * @Description 角色controller
 */
@RestController
@RequestMapping("role")
@Api(tags = "角色controller")
@Slf4j
public class RoleController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    RoleFace roleFace;

    /**
     * @Description 角色列表
     * @param roleVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询角色分页",notes = "查询角色分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleVo",value = "角色查询对象",required = false,dataType = "RoleVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<RoleVo>> findRoleVoPage(
        @RequestBody RoleVo roleVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize)  {
        Page<RoleVo> roleVoPage = roleFace.findRoleVoPage(roleVo, pageNum, pageSize);
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,roleVoPage);
    }


    /**
     * @Description 创建角色
     * @param roleVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "保存角色",notes = "保存角色")
    @ApiImplicitParam(name = "roleVo",value = "角色对象",required = true,dataType = "RoleVo")
    ResponseWrap<RoleVo> createRole(@RequestBody RoleVo roleVo)  {
        RoleVo roleVoResult = roleFace.createRole(roleVo);
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,roleVoResult);
    }

    /**
     * @Description 修改角色
     * @param roleVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改角色",notes = "修改角色")
    @ApiImplicitParam(name = "roleVo",value = "角色对象",required = true,dataType = "RoleVo")
    ResponseWrap<Boolean> updateRole(@RequestBody RoleVo roleVo)  {
        Boolean flag = roleFace.updateRole(roleVo);
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除角色
     * @param roleVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除角色",notes = "删除角色")
    @ApiImplicitParam(name = "roleVo",value = "角色查询对象",required = true,dataType = "RoleVo")
    ResponseWrap<Boolean> deleteRole(@RequestBody RoleVo roleVo ) {
        String[] checkedIds = roleVo.getCheckedIds();
        Boolean flag = roleFace.deleteRole(checkedIds);
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,flag);
    }

    /**
     * @Description 角色下拉框
     * @return
     */
    @PostMapping("init-roleid-options")
    @ApiOperation(value = "角色下拉框",notes = "删除角色")
    ResponseWrap<List<RoleVo>> initRoleIdOptions( ) {
        List<RoleVo> roleVoResult = roleFace.initRoleIdOptions();
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,roleVoResult);
    }

    @PostMapping("update-role-enableFlag")
    @ApiOperation(value = "修改角色状态",notes = "修改角色状态")
    @ApiImplicitParam(name = "roleVo",value = "角色对象",required = true,dataType = "RoleVo")
    ResponseWrap<Boolean> updateRoleEnableFlag(@RequestBody RoleVo roleVo)  {
        Boolean flag = roleFace.updateRole(roleVo);
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,flag);
    }
}
