package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.LogBusinessFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.LogBusinessEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName LogBusinessController.java
 * @Description 日志controller
 */
@RestController
@RequestMapping("log-business")
@Api(tags = "日志controller")
@Slf4j
public class LogBusinessController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    LogBusinessFace logBusinessFace;

    /**
     * @Description 日志列表
     * @param logBusinessVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询日志分页",notes = "查询日志分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "logBusinessVo",value = "日志查询对象",required = false,dataType = "LogBusinessVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<LogBusinessVo>> findLogBusinessVoPage(
        @RequestBody LogBusinessVo logBusinessVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize){
            Page<LogBusinessVo> logBusinessVoPage = logBusinessFace.findLogBusinessVoPage(logBusinessVo, pageNum, pageSize);
            return ResponseWrapBuild.build(LogBusinessEnum.SUCCEED,logBusinessVoPage);
    }
    
}
