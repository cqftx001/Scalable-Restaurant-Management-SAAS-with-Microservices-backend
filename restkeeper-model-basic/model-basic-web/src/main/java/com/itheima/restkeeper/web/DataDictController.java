package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.DataDictFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.DataDictEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.DataDictVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
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
 * @ClassName DataDictController.java
 * @Description 数字字典controller
 */
@RestController
@RequestMapping("data-dict")
@Api(tags = "数字字典controller")
@Slf4j
public class DataDictController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    DataDictFace dataDictFace;

    /***
     * @description 查询数据字典列表
     * @param dataDictVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.itheima.restkeeper.req.DataDictVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询字典分页",notes = "查询字典分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dataDictVo",value = "字典查询对象",required = false,dataType = "DataDictVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    ResponseWrap<Page<DataDictVo>> findDataDictVoPage(
        @RequestBody DataDictVo dataDictVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<DataDictVo> dataDictVoPage = dataDictFace.findDataDictVoPage(dataDictVo, pageNum, pageSize);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,dataDictVoPage);
    }

    /**
     * @Description 保存字典数据
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加字典",notes = "添加字典")
    @ApiImplicitParam(name = "dataDictVo",value = "字典信息",required = true,dataType = "DataDictVo")
    public ResponseWrap<DataDictVo> saveDataDict(@RequestBody DataDictVo dataDictVo) {
        DataDictVo dataDictVoResult = dataDictFace.saveDataDict(dataDictVo);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,dataDictVoResult);
    }

    /**
     * @Description 修改字典数据
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改字典",notes = "修改典")
    @ApiImplicitParam(name = "dataDictVo",value = "字典信息",required = true,dataType = "DataDictVo")
    public ResponseWrap<Boolean> updateDataDict(@RequestBody DataDictVo dataDictVo) {
        Boolean flag = dataDictFace.updateDataDict(dataDictVo);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,flag);
    }

    /**
     * @Description 根据主键查询数据字典
     * @return
     */
    @GetMapping("{dataDictId}")
    @ApiOperation(value = "根ID查询数据字典",notes = "根ID查询数据字典")
    @ApiImplicitParam(paramType = "path",name = "dataDictId",value = "字典ID",example = "1",dataType = "Integer")
    ResponseWrap<DataDictVo> findDataDictVoById(@PathVariable("dataDictId") String dataDictId) {
        DataDictVo dataDictVo = dataDictFace.findDataDictVoById(dataDictId);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,dataDictVo);
    }

    /**
     * @Description 禁用，启用数据字典
     * @return
     */
    @PatchMapping("/data-key/{enableFlag}")
    @ApiOperation(value = "修改字典状态",notes = "修改字典状态")
    @ApiImplicitParam(paramType = "path",name = "enableFlag",value = "状态",example = "YES",dataType = "String")
    ResponseWrap<Boolean> updateByDataKey(@RequestParam(value = "dataKeys") List<String> dataKeys,
                            @PathVariable("enableFlag") String enableFlag) {
        Boolean flag = dataDictFace.updateByDataKey(dataKeys, enableFlag);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,flag);
    }

    /**
     * @Description 根据dataKey获取value
     * @return
     */
    @GetMapping("data-key/{dataKey}")
    @ApiOperation(value = "根据dataKey获取value",notes = "根据dataKey获取value")
    @ApiImplicitParam(paramType = "path",name = "dataKey",value = "字典dataKey",example = "URGE_TYPE0",dataType = "String")
    ResponseWrap<String> findValueByDataKey(@PathVariable("dataKey") String dataKey) {
        String valueByDataKey = dataDictFace.findValueByDataKey(dataKey);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,valueByDataKey);
    }


    /**
     * @Description 根据parentKey获取value
     * @return
     */
    @GetMapping("parent-key/{parentKey}")
    @ApiOperation(value = "根据parentKey获取value",notes = "根据parentKey获取value")
    @ApiImplicitParam(paramType = "path",name = "parentKey",value = "字典parentKey",example = "URGE_TYPE",dataType = "String")
    ResponseWrap<List<DataDictVo>> findDataDictVoByParentKey(@PathVariable("parentKey") String parentKey) {
        List<DataDictVo> list = dataDictFace.findDataDictVoByParentKey(parentKey);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,list);
    }

    @PostMapping("update-dataDict-enableFlag")
    @ApiOperation(value = "修改数字字典状态",notes = "修改数字字典状态")
    @ApiImplicitParam(name = "dataDictVo",value = "字典信息",required = true,dataType = "DataDictVo")
    ResponseWrap<Boolean> updateDataDictEnableFlag(@RequestBody DataDictVo dataDictVo) {
        Boolean flag = dataDictFace.updateDataDict(dataDictVo);
        return ResponseWrapBuild.build(DataDictEnum.SUCCEED,flag);
    }
}

