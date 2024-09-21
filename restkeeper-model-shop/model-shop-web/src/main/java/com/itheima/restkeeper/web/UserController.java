package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.UserFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.UserEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import com.itheima.restkeeper.validation.Create;
import com.itheima.restkeeper.validation.Delete;
import com.itheima.restkeeper.validation.Update;
import com.itheima.restkeeper.validation.UpdateEnableFlag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * @Description 用户列表
     * @param userVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询用户分页",notes = "查询用户分页")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "userVo",value = "用户查询对象",required = true,dataType = "UserVo"),
       @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
       @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<UserVo>> findUserVoPage(
        @RequestBody UserVo userVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize)
        throws ProjectException {
        try {
            Page<UserVo> userVoPage = userFace.findUserVoPage(userVo, pageNum, pageSize);
            return ResponseWrapBuild.build(UserEnum.SUCCEED,userVoPage);
        } catch (Exception e) {
            log.error("查询用户列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.PAGE_FAIL);
        }
    }

    /**
     * @Description 注册用户
     * @param userVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "注册用户",notes = "注册用户")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<UserVo> registerUser(@Validated(Create.class) @RequestBody UserVo userVo) throws ProjectException {

        String plainPassword = userVo.getPassword();
        //必须要加{bcrypt}要不认证不通过
        String password = passwordEncoder.encode(plainPassword);
        userVo.setPassword(password);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userFace.createUser(userVo));
    }

    /**
     * @Description 修改用户
     * @param userVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改用户",notes = "修改用户")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> updateUser(@Validated(Update.class) @RequestBody UserVo userVo) throws ProjectException {
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
    ResponseWrap<Boolean> deleteUser(@Validated(Delete.class) @RequestBody UserVo userVo ) throws ProjectException {
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
    ResponseWrap<UserVo> findUserByUserId(@PathVariable("userId") Long userId) throws ProjectException {
        UserVo userVo = userFace.findUserByUserId(userId);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,userVo);
    }

    /**
     * @Description 查找用户list
     * @return
     */
    @GetMapping("select-list")
    @ApiOperation(value = "查找用户list",notes = "查找用户list")
    ResponseWrap<List<UserVo>> findUserVoList() throws ProjectException {
        List<UserVo> list = userFace.findUserVoList();
        return ResponseWrapBuild.build(UserEnum.SUCCEED,list);
    }

    @PostMapping("update-user-enableFlag")
    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    @ApiImplicitParam(name = "userVo",value = "用户查询对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> updateUserEnableFlag(@Validated(UpdateEnableFlag.class) @RequestBody UserVo userVo) throws ProjectException {
        Boolean flag = userFace.updateUser(userVo);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,flag);
    }

    @PostMapping("rest-password")
    @ApiOperation(value = "重置用户密码",notes = "重置用户密码")
    @ApiImplicitParam(name = "userVo",value = "用户对象",required = true,dataType = "UserVo")
    ResponseWrap<Boolean> restPssword(@RequestBody UserVo userVo) throws ProjectException {
        //必须要加{bcrypt}要不认证不通过
        String password = passwordEncoder.encode("88488");
        userVo.setPassword(password);
        Boolean flag = userFace.updateUser(userVo);
        return ResponseWrapBuild.build(UserEnum.SUCCEED,flag);
    }

}
