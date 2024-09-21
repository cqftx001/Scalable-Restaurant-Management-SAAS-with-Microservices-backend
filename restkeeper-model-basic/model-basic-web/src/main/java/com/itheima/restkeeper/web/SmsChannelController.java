package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsChannelFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.SmsChannelEnum;
import com.itheima.restkeeper.req.SmsChannelVo;
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
 * @ClassName SmsChannelController.java
 * @Description TODO
 */
@RestController
@RequestMapping("smsChannel")
@Slf4j
@Api(tags = "通道controller")
public class SmsChannelController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    SmsChannelFace smsChannelFace;

    /**
     * @Description 通道列表
     * @param smsChannelVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询通道分页",notes = "查询通道分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsChannelVo",value = "通道查询对象",required = true,dataType = "SmsChannelVo"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<SmsChannelVo>> findSmsChannelVoPage(
            @RequestBody SmsChannelVo smsChannelVo,
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {
        Page<SmsChannelVo> smsChannelVoPage = smsChannelFace.findSmsChannelVoPage(smsChannelVo, pageNum, pageSize);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,smsChannelVoPage);
    }

    /**
     * @Description 查询通道下拉框
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查询通道下拉框",notes = "查询通道下拉框")
    public ResponseWrap<List<SmsChannelVo>> findSmsChannelVoList() {
        List<SmsChannelVo> smsChannelVoPage = smsChannelFace.findSmsChannelVoList();
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,smsChannelVoPage);
    }

    /**
     * @Description 添加通道
     * @param smsChannelVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加通道",notes = "添加通道")
    @ApiImplicitParam(name = "smsChannelVo",value = "通道对象",required = true,dataType = "SmsChannelVo")
    ResponseWrap<SmsChannelVo> createSmsChannel(@RequestBody SmsChannelVo smsChannelVo) {
        SmsChannelVo smsChannelVoResult = smsChannelFace.createSmsChannel(smsChannelVo);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,smsChannelVoResult);
    }

    /**
     * @Description 修改通道
     * @param smsChannelVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改通道",notes = "修改通道")
    @ApiImplicitParam(name = "smsChannelVo",value = "通道对象",required = true,dataType = "SmsChannelVo")
    ResponseWrap<Boolean> updateSmsChannel(@RequestBody SmsChannelVo smsChannelVo) {
        Boolean flag = smsChannelFace.updateSmsChannel(smsChannelVo);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除通道
     * @param smsChannelVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除通道",notes = "删除通道")
    @ApiImplicitParam(name = "smsChannelVo",value = "通道查询对象",required = true,dataType = "SmsChannelVo")
    ResponseWrap<Boolean> deleteSmsChannel(@RequestBody SmsChannelVo smsChannelVo ) {
        String[] checkedIds = smsChannelVo.getCheckedIds();
        Boolean flag = smsChannelFace.deleteSmsChannel(checkedIds);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找通道
     * @param smsChannelId 通道Id
     * @return
     */
    @GetMapping("{smsChannelId}")
    @ApiOperation(value = "查找通道",notes = "查找通道")
    @ApiImplicitParam(paramType = "path",name = "smsChannelId",value = "通道Id",dataType = "Long")
    ResponseWrap<SmsChannelVo> findSmsChannelBySmsChannelId(@PathVariable("smsChannelId") Long smsChannelId) {
        SmsChannelVo smsChannelVo = smsChannelFace.findSmsChannelBySmsChannelId(smsChannelId);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,smsChannelVo);
    }

    @PostMapping("update-smsChannel-enableFlag")
    @ApiOperation(value = "修改通道状态",notes = "修改通道状态")
    @ApiImplicitParam(name = "smsChannelVo",value = "通道查询对象",required = true,dataType = "SmsChannelVo")
    ResponseWrap<Boolean> updateSmsChannelEnableFlag(@RequestBody SmsChannelVo smsChannelVo) {
        Boolean flag = smsChannelFace.updateSmsChannel(smsChannelVo);
        return ResponseWrapBuild.build(SmsChannelEnum.SUCCEED,flag);
    }
}
