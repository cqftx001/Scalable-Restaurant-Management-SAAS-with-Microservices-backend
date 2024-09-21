package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.TableAreaFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.TableAreaEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.TableAreaVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TableAreaController.java
 * @Description 区域Controller
 */
@RestController
@RequestMapping("table-area")
@Slf4j
@Api(tags = "区域controller")
public class TableAreaController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    TableAreaFace tableAreaFace;

    /**
     * @Description 区域列表
     * @param tableAreaVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询区域分页",notes = "查询区域分页")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "tableAreaVo",value = "区域查询对象",dataType = "TableAreaVo"),
       @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
       @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<TableAreaVo>> findTableAreaVoPage(
        @RequestBody TableAreaVo tableAreaVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<TableAreaVo> tableAreaVoPage = tableAreaFace.findTableAreaVoPage(tableAreaVo, pageNum, pageSize);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,tableAreaVoPage);
    }

    /**
     * @Description 添加区域
     * @param tableAreaVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加区域",notes = "添加区域")
    @ApiImplicitParam(name = "tableAreaVo",value = "区域对象",required = true,dataType = "TableAreaVo")
    ResponseWrap<TableAreaVo> createTableArea(@RequestBody TableAreaVo tableAreaVo) {
        TableAreaVo tableAreaVoResult = tableAreaFace.createTableArea(tableAreaVo);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,tableAreaVoResult);
    }

    /**
     * @Description 修改区域
     * @param tableAreaVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改区域",notes = "修改区域")
    @ApiImplicitParam(name = "tableAreaVo",value = "区域对象",required = true,dataType = "TableAreaVo")
    ResponseWrap<Boolean> updateTableArea(@RequestBody TableAreaVo tableAreaVo) {
        Boolean flag = tableAreaFace.updateTableArea(tableAreaVo);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除区域
     * @param tableAreaVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除区域",notes = "删除区域")
    @ApiImplicitParam(name = "tableAreaVo",value = "区域查询对象",required = true,dataType = "TableAreaVo")
    ResponseWrap<Boolean> deleteTableArea(@RequestBody TableAreaVo tableAreaVo ) {
        //获得所有选择的区域IDS
        String[] checkedIds = tableAreaVo.getCheckedIds();
        Boolean flag = tableAreaFace.deleteTableArea(checkedIds);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找区域
     * @param tableAreaId 区域id
     * @return
     */
    @GetMapping("{tableAreaId}")
    @ApiOperation(value = "查找区域",notes = "查找区域")
    @ApiImplicitParam(paramType = "path",name = "tableAreaId",value = "区域Id",example = "1",dataType = "Long")
    ResponseWrap<TableAreaVo> findTableAreaByTableAreaId(@PathVariable("tableAreaId") Long tableAreaId) {
        TableAreaVo tableAreaVo = tableAreaFace.findTableAreaByTableAreaId(tableAreaId);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED, tableAreaVo);
    }

    /**
     * @Description 查找区域
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查找区域列表",notes = "查找区域列表")
    ResponseWrap<List<TableAreaVo>> findTableAreaVoList() {
        List<TableAreaVo> list = tableAreaFace.findTableAreaVoList();
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,list);
    }

    @PostMapping("update-tableArea-enableFlag")
    @ApiOperation(value = "修改区域状态",notes = "修改区域状态")
    ResponseWrap<Boolean> updateTableAreaEnableFlag(@RequestBody TableAreaVo tableAreaVo) {
        Boolean flag = tableAreaFace.updateTableArea(tableAreaVo);
        return ResponseWrapBuild.build(TableAreaEnum.SUCCEED,flag);
    }
}
