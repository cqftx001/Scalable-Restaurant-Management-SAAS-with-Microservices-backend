package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.EnterpriseFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.EnterpriseEnum;
import com.itheima.restkeeper.enums.RoleEnum;
import com.itheima.restkeeper.req.EnterpriseVo;
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
 * @ClassName EnterpriseController.java
 * @Description 企业服务controller
 */
@RestController
@Slf4j
@RequestMapping("enterprise")
@Api(tags = "企业controller")
public class EnterpriseController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    EnterpriseFace enterpriseFace;

    /**
     * @Description 企业列表
     * @param enterpriseVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询企业分页",notes = "查询企业分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "enterpriseVo",value = "企业查询对象",required = false,dataType = "EnterpriseVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<EnterpriseVo>> findEnterpriseVoPage(
        @RequestBody EnterpriseVo enterpriseVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<EnterpriseVo> enterpriseVoPage = enterpriseFace.findEnterpriseVoPage(enterpriseVo, pageNum, pageSize);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,enterpriseVoPage);

    }


    /**
     * @Description 创建企业
     * @param enterpriseVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "保存企业",notes = "保存企业")
    @ApiImplicitParam(name = "enterpriseVo",value = "企业对象",required = true,dataType = "EnterpriseVo")
    ResponseWrap<EnterpriseVo> createEnterprise(@RequestBody EnterpriseVo enterpriseVo) {
        EnterpriseVo enterpriseVoResult = enterpriseFace.createEnterprise(enterpriseVo);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,enterpriseVoResult);
    }

    /**
     * @Description 修改企业
     * @param enterpriseVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改企业",notes = "修改企业")
    @ApiImplicitParam(name = "enterpriseVo",value = "企业对象",required = true,dataType = "EnterpriseVo")
    ResponseWrap<Boolean> updateEnterprise(@RequestBody EnterpriseVo enterpriseVo) {
        Boolean flag = enterpriseFace.updateEnterprise(enterpriseVo);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除企业
     * @param enterpriseVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除企业",notes = "删除企业")
    @ApiImplicitParam(name = "enterpriseVo",value = "企业查询对象",required = true,dataType = "EnterpriseVo")
    ResponseWrap<Boolean> deleteEnterprise(@RequestBody EnterpriseVo enterpriseVo ) {
        String[] checkedIds = enterpriseVo.getCheckedIds();
        Boolean flag = enterpriseFace.deleteEnterprise(checkedIds);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,flag);
    }

    /**
     * @Description 企业下拉框
     * @return
     */
    @PostMapping("init-enterprise-options")
    @ApiOperation(value = "企业下拉框",notes = "删除企业")
    ResponseWrap<List<EnterpriseVo>> initEnterpriseIdOptions( ) {
        List<EnterpriseVo> roleVoResult = enterpriseFace.initEnterpriseIdOptions();
        return ResponseWrapBuild.build(RoleEnum.SUCCEED,roleVoResult);
    }

    @PostMapping("update-enterprise-enableFlag")
    @ApiOperation(value = "修改企业状态",notes = "修改企业状态")
    ResponseWrap<Boolean> updateEnterpriseEnableFlag(@RequestBody EnterpriseVo enterpriseVo) {
        Boolean flag = enterpriseFace.updateEnterprise(enterpriseVo);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,flag);
    }

    @PostMapping("update-enterprise-auditStatus")
    @ApiOperation(value = "修改企业审核状态",notes = "修改企业审核状态")
    @ApiImplicitParam(name = "enterpriseVo",value = "企业查询对象",required = true,dataType = "EnterpriseVo")
    ResponseWrap<Boolean> updateEnterpriseAuditStatus(@RequestBody EnterpriseVo enterpriseVo) {
        Boolean flag = enterpriseFace.updateEnterprise(enterpriseVo);
        return ResponseWrapBuild.build(EnterpriseEnum.SUCCEED,flag);
    }

}
