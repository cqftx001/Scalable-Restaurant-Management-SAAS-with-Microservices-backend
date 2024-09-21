package com.itheima.restkeeper.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.CategoryFace;
import com.itheima.restkeeper.basic.ResponseWrap;
import com.itheima.restkeeper.enums.CategoryEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.CategoryVo;
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
 * @ClassName CategoryController.java
 * @Description 分类Controller
 */
@RestController
@RequestMapping("category")
@Slf4j
@Api(tags = "分类controller")
public class CategoryController {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    CategoryFace categoryFace;

    /**
     * @Description 分类列表
     * @param categoryVo 查询条件
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询分类分页",notes = "查询分类分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "categoryVo",value = "分类查询对象",required = false,dataType = "CategoryVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",dataType = "Integer")
    })
    public ResponseWrap<Page<CategoryVo>> findCategoryVoPage(
        @RequestBody CategoryVo categoryVo,
        @PathVariable("pageNum") int pageNum,
        @PathVariable("pageSize") int pageSize) {
        Page<CategoryVo> categoryVoPage = categoryFace.findCategoryVoPage(categoryVo, pageNum, pageSize);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,categoryVoPage);
    }

    /**
     * @Description 添加分类
     * @param categoryVo 对象信息
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加分类",notes = "添加分类")
    @ApiImplicitParam(name = "categoryVo",value = "分类对象",required = true,dataType = "CategoryVo")
    ResponseWrap<CategoryVo> createCategory(@RequestBody CategoryVo categoryVo) {
        CategoryVo categoryVoResult = categoryFace.createCategory(categoryVo);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,categoryVoResult);
    }

    /**
     * @Description 修改分类
     * @param categoryVo 对象信息
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "修改分类",notes = "修改分类")
    @ApiImplicitParam(name = "categoryVo",value = "分类对象",required = true,dataType = "CategoryVo")
    ResponseWrap<Boolean> updateCategory(@RequestBody CategoryVo categoryVo) {
        Boolean flag = categoryFace.updateCategory(categoryVo);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,flag);
    }

    /**
     * @Description 删除分类
     * @param categoryVo 查询对象
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类",notes = "删除分类")
    @ApiImplicitParam(name = "categoryVo",value = "分类查询对象",required = true,dataType = "CategoryVo")
    ResponseWrap<Boolean> deleteCategory(@RequestBody CategoryVo categoryVo ) {
        //获得所有选择分类IDS
        String[] checkedIds = categoryVo.getCheckedIds();
        Boolean flag = categoryFace.deleteCategory(checkedIds);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,flag);
    }

    /**
     * @Description 查找分类
     * @param categoryId 分类id
     * @return
     */
    @GetMapping("{categoryId}")
    @ApiOperation(value = "查找分类",notes = "查找分类")
    @ApiImplicitParam(paramType = "path",name = "categoryId",value = "分类Id",dataType = "Long")
    ResponseWrap<CategoryVo> findCategoryByCategoryId(@PathVariable("categoryId") Long categoryId) {
        CategoryVo categoryVo = categoryFace.findCategoryByCategoryId(categoryId);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,categoryVo);
    }

    /**
     * @Description 查找分类
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "查找分类列表",notes = "查找分类列表")
    ResponseWrap<List<CategoryVo>> findCategoryVoList() {
        List<CategoryVo> list = categoryFace.findCategoryVoList();
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,list);
    }


    @PostMapping("update-category-enableFlag")
    @ApiOperation(value = "修改分类状态",notes = "修改分类状态")
    ResponseWrap<Boolean> updateCategoryEnableFlag(@RequestBody CategoryVo categoryVo) {
        Boolean flag = categoryFace.updateCategory(categoryVo);
        return ResponseWrapBuild.build(CategoryEnum.SUCCEED,flag);
    }
}
