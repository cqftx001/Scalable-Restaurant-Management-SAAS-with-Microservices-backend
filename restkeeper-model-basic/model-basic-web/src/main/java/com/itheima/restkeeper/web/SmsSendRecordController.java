package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.SmsSendRecordFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.SmsSendRecordEnum;
import com.itheima.restkeeper.req.SmsSendRecordVo;
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
 * @ClassName SmsSendRecordController.java
 * @Description 发送记录Controller
 */
@RestController
@RequestMapping("smsSendRecord")
@Slf4j
@Api(tags = "发送记录controller")
public class SmsSendRecordController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    SmsSendRecordFace smsSendRecordFace;

    /**
     * @Description 发送记录列表
     * @param smsSendRecordVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询发送记录分页",notes = "查询发送记录分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "smsSendRecordVo",value = "发送记录查询对象",required = true,dataType = "SmsSendRecordVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<SmsSendRecordVo>> findSmsSendRecordVoPage(
        @RequestBody SmsSendRecordVo smsSendRecordVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<SmsSendRecordVo> smsSendRecordVoPage =
            smsSendRecordFace.findSmsSendRecordVoPage(
                smsSendRecordVo,
                pageNum,
                pageSize);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,smsSendRecordVoPage);
    }

    /**
     * @Description 修改发送记录
     * @param smsSendRecordVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改发送记录",notes = "修改发送记录")
    @ApiImplicitParam(name = "smsSendRecordVo",value = "发送记录对象",required = true,dataType = "SmsSendRecordVo")
    ResponseWrap<Boolean> updateSmsSendRecord(@RequestBody SmsSendRecordVo smsSendRecordVo) {
        Boolean flag = smsSendRecordFace.updateSmsSendRecord(smsSendRecordVo);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除发送记录
     * @param smsSendRecordVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除发送记录",notes = "删除发送记录")
    @ApiImplicitParam(name = "smsSendRecordVo",value = "发送记录查询对象",required = true,dataType = "SmsSendRecordVo")
    ResponseWrap<Boolean> deleteSmsSendRecord(@RequestBody SmsSendRecordVo smsSendRecordVo ) {
        String[] checkedIds = smsSendRecordVo.getCheckedIds();
        Boolean flag = smsSendRecordFace.deleteSmsSendRecord(checkedIds);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找发送记录
     * @param smsSendRecordId 登录名
     * @return
     */
    @GetMapping("{smsSendRecordId}")
    @ApiOperation(value = "查找发送记录",notes = "查找发送记录")
    @ApiImplicitParam(paramType = "path",name = "smsSendRecordId",value = "发送记录Id",dataType = "Long")
    ResponseWrap<SmsSendRecordVo> findSmsSendRecordBySmsSendRecordId(@PathVariable("smsSendRecordId") Long smsSendRecordId) {
        SmsSendRecordVo smsSendRecordVo = smsSendRecordFace.findSmsSendRecordBySmsSendRecordId(smsSendRecordId);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,smsSendRecordVo);
    }

    @PostMapping("update-smsSendRecord-enableFlag")
    @ApiOperation(value = "修改发送记录状态",notes = "修改发送记录状态")
    @ApiImplicitParam(name = "smsSendRecordVo",value = "发送记录查询对象",required = true,dataType = "SmsSendRecordVo")
    ResponseWrap<Boolean> updateSmsSendRecordEnableFlag(@RequestBody SmsSendRecordVo smsSendRecordVo) {
        Boolean flag = smsSendRecordFace.updateSmsSendRecord(smsSendRecordVo);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,flag);
    }

    @PostMapping("retrySendSms")
    @ApiOperation(value = "重发",notes = "重发")
    @ApiImplicitParam(name = "smsSendRecordVo",value = "发送记录查询对象",required = true,dataType = "SmsSendRecordVo")
    ResponseWrap<Boolean> retrySendSms(@RequestBody SmsSendRecordVo smsSendRecordVo) {
        Boolean flag = smsSendRecordFace.retrySendSms(smsSendRecordVo);
        return ResponseWrapBuild.build(SmsSendRecordEnum.SUCCEED,flag);
    }

}
