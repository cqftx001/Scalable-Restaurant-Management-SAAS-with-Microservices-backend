package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.ResourceFace;
import com.itheima.restkeeper.enums.ResourceEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.TreeVo;
import com.itheima.restkeeper.service.IResourceService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ResourceFaceImpl.java
 * @Description 资源服务实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
        methods ={
                @Method(name = "findResourceVoPage",retries = 2),
                @Method(name = "initResourceTreeVo",retries = 2),
                @Method(name = "createResource",retries = 0),
                @Method(name = "updateResource",retries = 0),
                @Method(name = "deleteResource",retries = 0)
        })
public class ResourceFaceImpl implements ResourceFace {

    @Autowired
    IResourceService resourceService;

    @Override
    public Page<ResourceVo> findResourceVoPage(ResourceVo resourceVo,
                                               int pageNum,
                                               int pageSize) throws ProjectException{
        try {
            Page<Resource> page = resourceService.findResourceVoPage(resourceVo, pageNum, pageSize);
            Page<ResourceVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Resource> routeList = page.getRecords();
            List<ResourceVo> routeVoList = BeanConv.toBeanList(routeList,ResourceVo.class);
            pageVo.setRecords(routeVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询资源列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.PAGE_FAIL);
        }
    }

    @Override
    public TreeVo initResourceTreeVo(String[] checkedIds)throws ProjectException {
        try {
            return resourceService.initResourceTreeVo(checkedIds);
        } catch (Exception e) {
            log.error("查询资源数异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.INIT_TREE_FAIL);
        }
    }

    @Override
    public ResourceVo createResource(ResourceVo resourceVo)throws ProjectException {
        try {
            return BeanConv.toBean(resourceService.createResource(resourceVo),ResourceVo.class);
        } catch (Exception e) {
            log.error("保存资源异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.CREATE_FAIL);
        }

    }

    @Override
    public Boolean updateResource(ResourceVo resourceVo)throws ProjectException {
        try {
            return resourceService.updateResource(resourceVo);
        } catch (Exception e) {
            log.error("保存资源异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean deleteResource(String[] checkedIds) throws ProjectException{
        try {
            return resourceService.deleteResource(checkedIds);
        } catch (Exception e) {
            log.error("删除资源异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.DELETE_FAIL);
        }

    }
}
