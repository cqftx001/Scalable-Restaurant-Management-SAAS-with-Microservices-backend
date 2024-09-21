package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.BrandFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.BrandEnum;
import com.itheima.restkeeper.req.BrandVo;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BrandController.java
 * @Description 品牌Controller
 */
@RestController
@RequestMapping("brand")
@Slf4j
@Api(tags = "品牌controller")
public class BrandController {

    @DubboReference(version = "${dubbo.application.version}",check = false, timeout = 9000000)
    BrandFace brandFace;

    /**
     * @Description 品牌列表
     * @param brandVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询品牌分页",notes = "查询品牌分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "brandVo",value = "品牌查询对象",required = true,dataType = "BrandVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<BrandVo>> findBrandVoPage(
        @RequestBody BrandVo brandVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<BrandVo> brandVoPage = brandFace.findBrandVoPage(brandVo, pageNum, pageSize);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,brandVoPage);
    }

    /**
     * @Description 查询品牌下拉框
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查询品牌下拉框",notes = "查询品牌下拉框")
    public ResponseWrap<List<BrandVo>> findBrandVoList() {
        List<BrandVo> brandVoPage = brandFace.findBrandVoList();
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,brandVoPage);
    }

    /**
     * @Description 添加品牌
     * @param brandVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加品牌",notes = "添加品牌")
    @ApiImplicitParam(name = "brandVo",value = "品牌对象",required = true,dataType = "BrandVo")
    @GlobalTransactional(rollbackFor=Exception.class)
    ResponseWrap<BrandVo> createBrand(@RequestBody BrandVo brandVo) {
        BrandVo brandVoResult = brandFace.createBrand(brandVo);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,brandVoResult);
    }

    /**
     * @Description 修改品牌
     * @param brandVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改品牌",notes = "修改品牌")
    @ApiImplicitParam(name = "brandVo",value = "品牌对象",required = true,dataType = "BrandVo")
    ResponseWrap<Boolean> updateBrand(@RequestBody BrandVo brandVo) {
        Boolean flag = brandFace.updateBrand(brandVo);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除品牌
     * @param brandVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除品牌",notes = "删除品牌")
    @ApiImplicitParam(name = "brandVo",value = "品牌查询对象",required = true,dataType = "BrandVo")
    ResponseWrap<Boolean> deleteBrand(@RequestBody BrandVo brandVo ) {
        String[] checkedIds = brandVo.getCheckedIds();
        Boolean flag = brandFace.deleteBrand(checkedIds);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找品牌
     * @param brandId 登录名
     * @return
     */
    @GetMapping("{brandId}")
    @ApiOperation(value = "查找品牌",notes = "查找品牌")
    @ApiImplicitParam(paramType = "path",name = "brandId",value = "品牌Id",dataType = "Long")
    ResponseWrap<BrandVo> findBrandByBrandId(@PathVariable("brandId") Long brandId) {
        BrandVo brandVo = brandFace.findBrandByBrandId(brandId);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,brandVo);
    }

    @PostMapping("update-brand-enableFlag")
    @ApiOperation(value = "修改品牌状态",notes = "修改品牌状态")
    @ApiImplicitParam(name = "brandVo",value = "品牌查询对象",required = true,dataType = "BrandVo")
    ResponseWrap<Boolean> updateBrandEnableFlag(@RequestBody BrandVo brandVo) {
        Boolean flag = brandFace.updateBrand(brandVo);
        return ResponseWrapBuild.build(BrandEnum.SUCCEED,flag);
    }

}
