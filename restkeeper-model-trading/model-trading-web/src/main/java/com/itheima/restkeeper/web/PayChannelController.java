package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.PayChannelFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.PayChannelEnum;
import com.itheima.restkeeper.req.PayChannelVo;
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
 * @ClassName PayChannelController.java
 * @Description 支付通道
 */
@RestController
@RequestMapping("payChannel")
@Slf4j
@Api(tags = "支付通道controller")
public class PayChannelController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    PayChannelFace payChannelFace;

    /**
     * @Description 支付通道列表
     * @param payChannelVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询支付通道分页",notes = "查询支付通道分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payChannelVo",value = "支付通道查询对象",required = true,dataType = "PayChannelVo"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<PayChannelVo>> findPayChannelVoPage(
            @RequestBody PayChannelVo payChannelVo,
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {
        Page<PayChannelVo> payChannelVoPage = payChannelFace.findPayChannelVoPage(payChannelVo, pageNum, pageSize);
        return ResponseWrapBuild.build(PayChannelEnum.SUCCEED,payChannelVoPage);
    }

    /**
     * @Description 添加支付通道
     * @param payChannelVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加支付通道",notes = "添加支付通道")
    @ApiImplicitParam(name = "payChannelVo",value = "支付通道对象",required = true,dataType = "PayChannelVo")
    ResponseWrap<PayChannelVo> createPayChannel(@RequestBody PayChannelVo payChannelVo) {
        PayChannelVo payChannelVoResult = payChannelFace.createPayChannel(payChannelVo);
        return ResponseWrapBuild.build(PayChannelEnum.SUCCEED,payChannelVoResult);
    }

    /**
     * @Description 修改支付通道
     * @param payChannelVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改支付通道",notes = "修改支付通道")
    @ApiImplicitParam(name = "payChannelVo",value = "支付通道对象",required = true,dataType = "PayChannelVo")
    ResponseWrap<Boolean> updatePayChannel(@RequestBody PayChannelVo payChannelVo) {
        Boolean flag = payChannelFace.updatePayChannel(payChannelVo);
        return ResponseWrapBuild.build(PayChannelEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除支付通道
     * @param payChannelVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除支付通道",notes = "删除支付通道")
    @ApiImplicitParam(name = "payChannelVo",value = "支付通道查询对象",required = true,dataType = "PayChannelVo")
    ResponseWrap<Boolean> deletePayChannel(@RequestBody PayChannelVo payChannelVo ) {
        String[] checkedIds = payChannelVo.getCheckedIds();
        Boolean flag = payChannelFace.deletePayChannel(checkedIds);
        return ResponseWrapBuild.build(PayChannelEnum.SUCCEED,flag);
    }

    @PostMapping("update-payChannel-enableFlag")
    @ApiOperation(value = "修改支付通道状态",notes = "修改支付通道状态")
    @ApiImplicitParam(name = "payChannelVo",value = "支付通道查询对象",required = true,dataType = "PayChannelVo")
    ResponseWrap<Boolean> updatePayChannelEnableFlag(@RequestBody PayChannelVo payChannelVo) {
        Boolean flag = payChannelFace.updatePayChannel(payChannelVo);
        return ResponseWrapBuild.build(PayChannelEnum.SUCCEED,flag);
    }
}
