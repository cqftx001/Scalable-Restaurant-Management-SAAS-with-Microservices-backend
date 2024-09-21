package com.itheima.restkeeper.web;

import com.itheima.restkeeper.MenusFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.MenusEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.MenuVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName MenusController.java
 * @Description 菜单controller
 */
@RestController
@RequestMapping("menus")
@Slf4j
@Api(tags = "菜单controller")
public class MenusController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    MenusFace menusFace;

    /**
     * @Description 查询系统菜单
     * @param systemCode 系统编码
     * @return
     */
    @GetMapping("select-by-systemCode/{systemCode}")
    @ApiOperation(value ="查询系统菜单",notes = "查询系统菜单")
    @ApiImplicitParam(paramType = "path",name = "systemCode",value = "系统编号",example = "应用systemCode",dataType = "String")
    ResponseWrap<List<MenuVo>> findMenusBySystemCode(@PathVariable("systemCode") String systemCode) {
        List<MenuVo> menus = menusFace.findMenusBySystemCode(systemCode);
        return ResponseWrapBuild.build(MenusEnum.SUCCEED,menus);
    }

}
