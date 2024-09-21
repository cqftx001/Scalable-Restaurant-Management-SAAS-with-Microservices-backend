package com.itheima.restkeeper.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.UserAdapterFace;
import com.itheima.restkeeper.UserFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.BasicEnum;
import com.itheima.restkeeper.enums.UserEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.RoleVo;
import com.itheima.restkeeper.req.SendMessageVo;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import com.itheima.restkeeper.utils.UserVoContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @ClassName UserController.java
 * @Description 用户Controller
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api(tags = "用户controller")
public class UserController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    UserFace userFace;

    @DubboReference(version = "${dubbo.application.version}",check = false)
    UserAdapterFace userAdapterFace;


    @PostMapping(value = "login")
    @ApiOperation(value = "用户登陆",notes = "用户登陆")
    void loginUser(String username,
                   String password,
                   String usertype)  {
        log.info("执行登录");
    }

    @PostMapping(value = "logout")
    @ApiOperation(value = "用户退出",notes = "用户退出")
    ResponseWrap<Boolean> logoutUser()  {
        return ResponseWrapBuild.build(UserEnum.LOGOUT_SUCCEED,true);
    }

    /**
     * @Description 用户列表
     * @param userVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询用户分页",notes = "查询用户分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userVo",value = "用户查询对象",required = false,dataType = "UserVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<UserVo>> findUserVoPage(
        @RequestBody UserVo userVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize)  {
        Page<UserVo> userVoPage = userFace.findUserVoPage(userVo, pageNum, pageSize);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userVoPage);
    }

    /**
     * @Description 注册用户
     * @param userVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "注册用户",notes = "注册用户")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<UserVo> registerUser(@RequestBody UserVo userVo)  {
        UserVo userVoResult = userFace.createUser(userVo);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userVoResult);
    }

    /**
     * @Description 修改用户
     * @param userVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改用户",notes = "修改用户")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> updateUser(@RequestBody UserVo userVo)  {
        Boolean flag = userFace.updateUser(userVo);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除用户
     * @param userVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除用户",notes = "删除用户")
    @ApiImplicitParam(name = "userVo",value = "用户查询对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> deleteUser(@RequestBody UserVo userVo ) {
        String[] checkedIds = userVo.getCheckedIds();
        Boolean flag = userFace.deleteUser(checkedIds);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找用户
     * @param userId 登录名
     * @return
     */
    @GetMapping("select-by-userId/{userId}")
    @ApiOperation(value = "查找用户",notes = "查找用户")
    @ApiImplicitParam(paramType = "path",name = "userId",value = "用户Id",example = "1",dataType = "Long")
    ResponseWrap<UserVo> findUserByUserId(@PathVariable("userId") Long userId)  {
        UserVo userVo = userFace.findUserByUserId(userId);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userVo);
    }

    /**
     * @Description 查找用户list
     * @return
     */
    @GetMapping("select-list")
    @ApiOperation(value = "查找用户list",notes = "查找用户list")
    ResponseWrap<List<UserVo>> findUserVoList()  {
        List<UserVo> list = userFace.findUserVoList();
        return ResponseWrapBuild.build(UserEnum.SUCCEED,list);
    }

    /**
     * @Description 查找用户有角色
     * @param userId 用户Id
     * @return
     */
    @GetMapping("role-by-userId/{userId}")
    @ApiOperation(value = "查找用户有角色",notes = "查找用户有角色")
    @ApiImplicitParam(paramType = "path",name = "userId",value = "用户ID",example = "1",dataType = "Long")
    ResponseWrap<List<RoleVo>> findRoleByUserId(@PathVariable("userId") Long userId)  {
        List<RoleVo> roleVos = userAdapterFace.findRoleByUserId(userId);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,roleVos);
    }

    /**
     * @Description 查询用户有资源
     * @param userId 用户Id
     * @return
     */
    @GetMapping("resource-by-userId/{userId}")
    @ApiOperation(value = "查询用户有资源",notes = "查询用户有资源")
    @ApiImplicitParam(paramType = "path",name = "userId",value = "用户ID",example = "1",dataType = "Long")
    ResponseWrap<List<ResourceVo>> findResourceByUserId(@PathVariable("userId") Long userId)  {
        List<ResourceVo> resourceVos = userAdapterFace.findResourceByUserId(userId);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,resourceVos);
    }

    @GetMapping("find-current-user")
    @ApiOperation(value = "查询当前用户",notes = "查询当前用户")
    ResponseWrap<UserVo> findCurrentUser()  {
        String currentUser = UserVoContext.getUserVoString();
        UserVo userVo = JSON.parseObject(currentUser, UserVo.class);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userVo);
    }

    @PostMapping("update-user-enableFlag")
    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> updateUserEnableFlag(@RequestBody UserVo userVo)  {
        Boolean flag = userFace.updateUserEnableFlag(userVo);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,flag);
    }

    /**
     * @Description 登录验证码
     * @param mobile 手机号码
     * @return
     */
    @PostMapping("loginCode/{mobile}")
    @ApiOperation(value = "登录验证码",notes = "登录验证码")
    @ApiImplicitParam(name = "mobile",value = "手机号",required = true,dataType = "String")
    ResponseWrap<Boolean> loginCode(@PathVariable("mobile")String mobile) {
        Boolean flag = userFace.SendloginCode(mobile);
        return ResponseWrapBuild.build(BasicEnum.SUCCEED,flag);
    }

}
