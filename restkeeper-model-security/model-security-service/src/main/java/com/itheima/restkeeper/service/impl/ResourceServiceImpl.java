package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.mapper.ResourceMapper;
import com.itheima.restkeeper.pojo.RoleResource;
import com.itheima.restkeeper.req.ResourceVo;
import com.itheima.restkeeper.req.TreeItem;
import com.itheima.restkeeper.req.TreeVo;
import com.itheima.restkeeper.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.service.IRoleResourceService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description：资源表 服务实现类
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    IRoleResourceService roleResourceService;

    @Override
    public Page<Resource> findResourceVoPage(ResourceVo resourceVo,int pageNum, int pageSize) {
        Page<Resource> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getParentId())
                && SuperConstant.ROOT_PARENT_ID.longValue()!=resourceVo.getParentId().longValue()) {
            queryWrapper.lambda().eq(Resource::getParentId,resourceVo.getParentId());
        }
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getLabel())) {
            queryWrapper.lambda().likeRight(Resource::getLabel,resourceVo.getLabel());
        }
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getResourceName())) {
            queryWrapper.lambda().likeRight(Resource::getResourceName,resourceVo.getResourceName());
        }
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getRequestPath())) {
            queryWrapper.lambda().likeRight(Resource::getRequestPath,resourceVo.getRequestPath());
        }
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getResourceType())) {
            queryWrapper.lambda().eq(Resource::getResourceType,resourceVo.getResourceType());
        }
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Resource::getEnableFlag,resourceVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByAsc(Resource::getSortNo);
        return page(page, queryWrapper);
    }

    @Override
    public TreeVo initResourceTreeVo(String[] checkedIds) {
        List<String> handlerList = new ArrayList<>();
        if (!EmptyUtil.isNullOrEmpty(checkedIds)){
            handlerList = Arrays.asList(checkedIds);
        }
        Resource resource = Resource.builder()
                .id(SuperConstant.ROOT_PARENT_ID)
                .description(SuperConstant.ROOT_PARENT_NAME)
                .isSystemRoot(SuperConstant.YES)
                .systemCode("super")
                .build();
        List<Resource> resources = new ArrayList<>();
        resources.add(resource);

        List<TreeItem> list  = new ArrayList<>();
        List<String> checkedList = new ArrayList<>();
        if (!EmptyUtil.isNullOrEmpty(checkedIds)){
            checkedList = Arrays.asList(checkedIds);
        }

        List<String> expandedIds = new ArrayList<>();

        recursionTreeItem(list,resources,checkedList,expandedIds);
        TreeVo treeVo = TreeVo.builder()
                .items(list)
                .checkedIds(checkedList)
                .expandedIds(expandedIds)
                .build();
        return treeVo;
    }

    /**
     * @Description 递归树形结构
     */
    private List<TreeItem> recursionTreeItem(List<TreeItem> list, List<Resource> resources,List<String> checkedList,List<String> expandedIds){
        for (Resource resource : resources) {
            TreeItem treeItem = TreeItem.builder()
                    .id(resource.getId())
                    .label(resource.getDescription())
                    .systemCode(resource.getSystemCode())
                    .build();
            if (!checkedList.isEmpty()&&checkedList.contains(resource.getId())){
                treeItem.setIsChecked(true);
            }else {
                treeItem.setIsChecked(false);
            }
            if (SuperConstant.YES.equals(resource.getIsSystemRoot())){
                expandedIds.add(String.valueOf(resource.getId()));
            }

            QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
            queryWrapper.lambda().eq(Resource::getParentId,resource.getId())
                    .orderByAsc(Resource::getSortNo);;
            List<Resource> resourceChildren = list(queryWrapper);
            if (resourceChildren.size()>0){
                List<TreeItem> listChildren  = Lists.newArrayList();
                this.recursionTreeItem(listChildren,resourceChildren,checkedList,expandedIds);
                treeItem.setChildren(listChildren);
            }
            list.add(treeItem);
        }
        return list;
    }

    @Override
    public Resource createResource(ResourceVo resourceVo) {
        Resource resource = BeanConv.toBean(resourceVo, Resource.class);
        if (SuperConstant.ROOT_PARENT_ID.longValue()==resourceVo.getParentId().longValue()){
            resource.setIsSystemRoot(SuperConstant.YES);
        }else {
            resource.setIsSystemRoot(SuperConstant.NO);
        }
        boolean flag = save(resource);
        if (flag){
            return resource;
        }
        return null;
    }

    @Override
    public Boolean updateResource(ResourceVo resourceVo) {
        Resource resource = BeanConv.toBean(resourceVo, Resource.class);
        return updateById(resource);
    }

    @Override
    @Transactional
    public Boolean deleteResource(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        List<Resource> resources = listByIds(idsLong);
        boolean flag = removeByIds(idsLong);
        if (flag){
            QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(RoleResource::getResourceId,idsLong);
            flag= roleResourceService.remove(queryWrapper);
        }
        return flag;
    }
}
