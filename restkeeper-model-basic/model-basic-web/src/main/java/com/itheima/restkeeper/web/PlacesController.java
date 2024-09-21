package com.itheima.restkeeper.web;

import com.itheima.restkeeper.PlacesFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.PlacesEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.PlacesVo;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PlacesController.java
 * @Description 地方
 */
@RestController
@RequestMapping("places")
@Api(tags = "地方controller")
@Slf4j
public class PlacesController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    PlacesFace placesFace;

    /**
     * @Description 查询省市区
     * @param parentId 父ID
     * @return
     */
    @GetMapping("{parentId}")
    @ApiOperation(value = "查询省市区",notes = "查询省市区")
    @ApiImplicitParam(paramType = "path",name = "parentId",value = "父ID",example = "10",dataType = "Integer")
    public ResponseWrap<List<PlacesVo>> findPlacesVoListByParentId(@PathVariable("parentId") Long parentId){
        try {
            List<PlacesVo> list = placesFace.findPlacesVoListByParentId(parentId);
            return ResponseWrapBuild.build(PlacesEnum.SUCCEED,list);
        } catch (Exception e) {
            log.error("查询查询省市区异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PlacesEnum.PAGE_FAIL);
        }
    }
}
