package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsTemplateFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.SmsTemplateEnum;
import com.itheima.restkeeper.req.SmsTemplateVo;
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
 * @ClassName SmsTemplateController.java
 * @Description TODO
 */
@RestController
@RequestMapping("smsTemplate")
@Slf4j
@Api(tags = "模板controller")
public class SmsTemplateController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    SmsTemplateFace smsTemplateFace;

    /**
     * @Description 模板列表
     * @param smsTemplateVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询模板分页",notes = "查询模板分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "smsTemplateVo",value = "模板查询对象",required = true,dataType = "SmsTemplateVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<SmsTemplateVo>> findSmsTemplateVoPage(
        @RequestBody SmsTemplateVo smsTemplateVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<SmsTemplateVo> smsTemplateVoPage = smsTemplateFace.findSmsTemplateVoPage(smsTemplateVo, pageNum, pageSize);
        return ResponseWrapBuild.build(SmsTemplateEnum.SUCCEED,smsTemplateVoPage);
    }

    /**
     * @Description 添加模板
     * @param smsTemplateVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加模板",notes = "添加模板")
    @ApiImplicitParam(name = "smsTemplateVo",value = "模板对象",required = true,dataType = "SmsTemplateVo")
    ResponseWrap<SmsTemplateVo> createSmsTemplate(@RequestBody SmsTemplateVo smsTemplateVo) {
        SmsTemplateVo smsTemplateVoResult = smsTemplateFace.addSmsTemplate(smsTemplateVo);
        return ResponseWrapBuild.build(SmsTemplateEnum.SUCCEED,smsTemplateVoResult);
    }

    /**
     * @Description 修改模板
     * @param smsTemplateVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改模板",notes = "修改模板")
    @ApiImplicitParam(name = "smsTemplateVo",value = "模板对象",required = true,dataType = "SmsTemplateVo")
    ResponseWrap<Boolean> updateSmsTemplate(@RequestBody SmsTemplateVo smsTemplateVo) {
        Boolean flag = smsTemplateFace.modifySmsTemplate(smsTemplateVo);
        return ResponseWrapBuild.build(SmsTemplateEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除模板
     * @param smsTemplateVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除模板",notes = "删除模板")
    @ApiImplicitParam(name = "smsTemplateVo",value = "模板查询对象",required = true,dataType = "SmsTemplateVo")
    ResponseWrap<Boolean> deleteSmsTemplate(@RequestBody SmsTemplateVo smsTemplateVo ) {
        String[] checkedIds = smsTemplateVo.getCheckedIds();
        Boolean flag = smsTemplateFace.deleteSmsTemplate(checkedIds);
        return ResponseWrapBuild.build(SmsTemplateEnum.SUCCEED,flag);
    }

    @PostMapping("update-smsTemplate-enableFlag")
    @ApiOperation(value = "修改模板状态",notes = "修改模板状态")
    @ApiImplicitParam(name = "smsTemplateVo",value = "模板查询对象",required = true,dataType = "SmsTemplateVo")
    ResponseWrap<Boolean> updateSmsTemplateEnableFlag(@RequestBody SmsTemplateVo smsTemplateVo) {
        Boolean flag = smsTemplateFace.updateSmsTemplateEnableFlag(smsTemplateVo);
        return ResponseWrapBuild.build(SmsTemplateEnum.SUCCEED,flag);
    }
}
