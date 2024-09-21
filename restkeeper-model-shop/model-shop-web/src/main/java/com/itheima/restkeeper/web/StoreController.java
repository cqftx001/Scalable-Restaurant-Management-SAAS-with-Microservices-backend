package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.StoreFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.StoreEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.StoreVo;
import com.itheima.restkeeper.utils.EmptyUtil;
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
 * @ClassName StoreController.java
 * @Description 门店Controller
 */
@RestController
@RequestMapping("store")
@Slf4j
@Api(tags = "门店controller")
public class StoreController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    StoreFace storeFace;

    /**
     * @Description 门店列表
     * @param storeVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询门店分页",notes = "查询门店分页")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "storeVo",value = "门店查询对象",required = true,dataType = "StoreVo"),
       @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
       @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<StoreVo>> findStoreVoPage(
        @RequestBody StoreVo storeVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<StoreVo> storeVoPage = storeFace.findStoreVoPage(storeVo, pageNum, pageSize);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,storeVoPage);
    }

    /**
     * @Description 添加门店
     * @param storeVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加门店",notes = "添加门店")
    @ApiImplicitParam(name = "storeVo",value = "门店对象",required = true,dataType = "StoreVo")
    ResponseWrap<StoreVo> createStore(@RequestBody StoreVo storeVo) {
        StoreVo storeVoResult = storeFace.createStore(storeVo);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,storeVoResult);
    }

    /**
     * @Description 修改门店
     * @param storeVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改门店",notes = "修改门店")
    @ApiImplicitParam(name = "storeVo",value = "门店对象",required = true,dataType = "StoreVo")
    ResponseWrap<Boolean> updateStore(@RequestBody StoreVo storeVo) {
        Boolean flag = storeFace.updateStore(storeVo);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除门店
     * @param storeVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除门店",notes = "删除门店")
    @ApiImplicitParam(name = "storeVo",value = "门店查询对象",required = true,dataType = "StoreVo")
    ResponseWrap<Boolean> deleteStore(@RequestBody StoreVo storeVo ) {
        String[] checkedIds = storeVo.getCheckedIds();
        Boolean flag = storeFace.deleteStore(checkedIds);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找门店
     * @param storeId 门店id
     * @return
     */
    @GetMapping("{storeId}")
    @ApiOperation(value = "查找门店",notes = "查找门店")
    @ApiImplicitParam(paramType = "path",name = "storeId",value = "门店Id",dataType = "Long")
    ResponseWrap<StoreVo> findStoreByStoreId(@PathVariable("storeId") Long storeId) {
        StoreVo storeVo = storeFace.findStoreByStoreId(storeId);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,storeVo);
    }

    /**
     * @Description 查找门店
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查找门店列表",notes = "查找门店列表")
    ResponseWrap<List<StoreVo>> findStoreVoList() {
        List<StoreVo> list = storeFace.findStoreVoList();
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,list);
    }

    /**
     * @Description 修改门店状态
     * @return
     */
    @PostMapping("update-store-enableFlag")
    @ApiOperation(value = "修改门店状态",notes = "修改门店状态")
    ResponseWrap<Boolean> updateStoreEnableFlag(@RequestBody StoreVo storeVo) {
        Boolean flag = storeFace.updateStore(storeVo);
        return ResponseWrapBuild.build(StoreEnum.SUCCEED,flag);
    }

}
