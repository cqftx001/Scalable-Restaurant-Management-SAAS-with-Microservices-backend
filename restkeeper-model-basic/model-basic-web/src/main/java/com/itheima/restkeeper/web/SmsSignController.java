package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsSignFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.req.SmsSignVo;
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
 * @ClassName SmsSignController.java
 * @Description TODO
 */
@RestController
@RequestMapping("smsSign")
@Slf4j
@Api(tags = "签名controller")
public class SmsSignController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    SmsSignFace smsSignFace;

    /**
     * @Description 签名列表
     * @param smsSignVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询签名分页",notes = "查询签名分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "smsSignVo",value = "签名查询对象",required = true,dataType = "SmsSignVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<SmsSignVo>> findSmsSignVoPage(
        @RequestBody SmsSignVo smsSignVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<SmsSignVo> smsSignVoPage = smsSignFace.findSmsSignVoPage(smsSignVo, pageNum, pageSize);
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,smsSignVoPage);
    }

    /**
     * @Description 查询签名下拉框
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查询签名下拉框",notes = "查询签名下拉框")
    public ResponseWrap<List<SmsSignVo>> findSmsSignVoList() {
        List<SmsSignVo> smsSignVoPage = smsSignFace.findSmsSignVoList();
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,smsSignVoPage);
    }

    /**
     * @Description 添加签名
     * @param smsSignVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加签名",notes = "添加签名")
    @ApiImplicitParam(name = "smsSignVo",value = "签名对象",required = true,dataType = "SmsSignVo")
    ResponseWrap<SmsSignVo> createSmsSign(@RequestBody SmsSignVo smsSignVo)  {
        SmsSignVo smsSignVoResult = smsSignFace.addSmsSign(smsSignVo);
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,smsSignVoResult);
    }

    /**
     * @Description 修改签名
     * @param smsSignVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改签名",notes = "修改签名")
    @ApiImplicitParam(name = "smsSignVo",value = "签名对象",required = true,dataType = "SmsSignVo")
    ResponseWrap<Boolean> updateSmsSign(@RequestBody SmsSignVo smsSignVo)  {
        Boolean flag = smsSignFace.modifySmsSign(smsSignVo);
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除签名
     * @param smsSignVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除签名",notes = "删除签名")
    @ApiImplicitParam(name = "smsSignVo",value = "签名查询对象",required = true,dataType = "SmsSignVo")
    ResponseWrap<Boolean> deleteSmsSign(@RequestBody SmsSignVo smsSignVo )  {
        String[] checkedIds = smsSignVo.getCheckedIds();
        Boolean flag = smsSignFace.deleteSmsSign(checkedIds);
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,flag);
    }


    @PostMapping("update-smsSign-enableFlag")
    @ApiOperation(value = "修改签名状态",notes = "修改签名状态")
    @ApiImplicitParam(name = "smsSignVo",value = "签名查询对象",required = true,dataType = "SmsSignVo")
    ResponseWrap<Boolean> updateSmsSignEnableFlag(@RequestBody SmsSignVo smsSignVo)  {
        Boolean flag = smsSignFace.updateSmsSignEnableFlag(smsSignVo);
        return ResponseWrapBuild.build(SmsSignEnum.SUCCEED,flag);
    }
}
