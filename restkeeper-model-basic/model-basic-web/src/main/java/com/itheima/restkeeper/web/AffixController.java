package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.AffixEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.UploadMultipartFile;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName AffixController.java
 * @Description 附件controller
 */
@RestController
@RequestMapping("affix")
@Api(tags = "附件controller")
@Slf4j
public class AffixController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    AffixFace affixFace;


    /***
     * @description 文件上传
     * @param file 上传对象
     * @return: com.itheima.travel.req.AffixVo
     */
    @PostMapping(value = "upload")
    @ApiOperation(value = "文件上传",notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "file", value = "文件对象", required = true, dataType = "__file"),
            @ApiImplicitParam(paramType = "path", name = "businessType",value = "业务类型",dataType = "String")
    })
    public ResponseWrap<AffixVo> upLoad(
            @RequestParam("file")MultipartFile file,
            AffixVo affixVo) throws IOException {
        //构建文件上次对象
        UploadMultipartFile uploadMultipartFile = UploadMultipartFile
                .builder()
                .originalFilename(file.getOriginalFilename())
                .fileByte(IOUtils.toByteArray(file.getInputStream()))
                .build();
        //执行文件上传
        AffixVo affixVoResult = affixFace.upLoad(uploadMultipartFile, affixVo);
        return ResponseWrapBuild.build(AffixEnum.SUCCEED,affixVoResult);

    }


    /**
     * @description 按业务ID查询附件
     * @param  affixVo 附件对象
     * @return java.util.List<com.itheima.travel.req.AffixVo>
     */
    @PostMapping("/select-by-businessId")
    @ApiOperation(value = "查询业务对应附件",notes = "查询业务对应附件")
    @ApiImplicitParam(name = "affixVo",value = "附件对象",required = true,dataType = "AffixVo")
    public ResponseWrap<List<AffixVo>> findAffixVoByBusinessId(@RequestBody AffixVo affixVo)  {
        //查询所有有效状态的附件
        List<AffixVo> AffixVoList = affixFace.findAffixVoByBusinessId(affixVo.getBusinessId());
        return ResponseWrapBuild.build(AffixEnum.SUCCEED,AffixVoList);
    }

    /**
     * @Description 删除业务相关附件
     * @param affixVo 附件信息
     * @return
     */
    @DeleteMapping("/delete-by-businessId")
    @ApiOperation(value = "删除业务对应附件",notes = "删除业务对应附件")
    @ApiImplicitParam(name = "affixVo",value = "附件对象",required = true,dataType = "AffixVo")
    public ResponseWrap<Boolean> deleteAffixVoByBusinessId(@RequestBody AffixVo affixVo) {
        //删除业务对应的所有附件信息
        Boolean flag = affixFace.deleteAffixVoByBusinessId(affixVo.getBusinessId());
        return ResponseWrapBuild.build(AffixEnum.SUCCEED,flag);
    }

    /***
     * @description 图片列表
     *
     * @param affixVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.itheima.restkeeper.req.DataDictVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询图片分页",notes = "查询图片分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "affixVo",value = "图片查询对象",required = false,dataType = "AffixVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseWrap<Page<AffixVo>> findAffixVoPage(
        @RequestBody AffixVo affixVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        //查询附件分页信息
        Page<AffixVo> affixVoPage = affixFace.findAffixVoPage(affixVo, pageNum, pageSize);
        return ResponseWrapBuild.build(AffixEnum.SUCCEED,affixVoPage);
    }

    /**
     * @Description 删除图片
     * @param affixVo 查询条件
     * @return
     */
    @DeleteMapping("/delete-by-affixId")
    @ApiOperation(value = "删除图片",notes = "删除图片")
    @ApiImplicitParam(name = "affixVo",value = "附件对象",required = true,dataType = "AffixVo")
    public ResponseWrap<Boolean> deleteAffix(@RequestBody AffixVo affixVo) {
        //删除图片信息
        Boolean flag = affixFace.deleteAffix(affixVo.getCheckedIds());
        return ResponseWrapBuild.build(AffixEnum.SUCCEED,flag);
    }

}

