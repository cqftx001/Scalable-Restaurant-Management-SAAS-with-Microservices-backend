package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.ResourceMapper;
import com.itheima.restkeeper.pojo.Resource;
import com.itheima.restkeeper.pojo.Role;
import com.itheima.restkeeper.req.MenuMetaVo;
import com.itheima.restkeeper.req.MenuVo;
import com.itheima.restkeeper.service.IMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenusServiceImpl.java
 * @Description 菜单业务服务
 */
@Service
public class MenusServiceImpl implements IMenusService {

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public List<MenuVo> findMenusBySystemCode(String systemCode) {
        //查询当前系统的根节点
        QueryWrapper<Resource> parentQueryWrapper =new QueryWrapper<>();
        parentQueryWrapper.lambda()
                .eq(Resource::getSystemCode,systemCode)
                .eq(Resource::getParentId,SuperConstant.ROOT_PARENT_ID)
                .orderByAsc(Resource::getSortNo);
        Resource parentResource = resourceMapper.selectOne(parentQueryWrapper);
        //构建一级菜单
        QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Resource::getParentId,parentResource.getId())
                .eq(Resource::getResourceType,SuperConstant.MENU)
                .orderByAsc(Resource::getSortNo);
        List<Resource> resources = resourceMapper.selectList(queryWrapper);
        List<MenuVo> list  = new ArrayList<>();
        recursionMenuVo(list,resources,SuperConstant.COMPONENT_LAYOUT);
        return list;
    }

    /**
     * @Description 递归菜单
     */
    public List<MenuVo> recursionMenuVo(List<MenuVo> list,List<Resource> resources,String component){

        for (Resource resource : resources) {
            List<Role> roles = resourceMapper.findRoleByResourceId(SuperConstant.YES,resource.getId());
            List<String> roleLabels = new ArrayList<>();
            roles.forEach(n->{
                roleLabels.add(n.getLabel());
            });
            MenuMetaVo menuMetaVo = MenuMetaVo.builder()
                    .icon(resource.getIcon())
                    .roles(roleLabels)
                    .title(resource.getResourceName())
                    .build();
            MenuVo menuVo = MenuVo.builder()
                    .name(resource.getResourceName())
                    .hidden(false)
                    .component(resource.getRequestPath())
                    .meta(menuMetaVo)
                    .build();
            if (SuperConstant.COMPONENT_LAYOUT.equals(component)){
                menuVo.setPath("/"+resource.getRequestPath());
                menuVo.setComponent(SuperConstant.COMPONENT_LAYOUT);
            }else {
                menuVo.setPath(resource.getRequestPath());
                menuVo.setComponent(component+"/"+resource.getRequestPath());
            }
            QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(Resource::getParentId,resource.getId())
                    .eq(Resource::getResourceType,SuperConstant.MENU)
                    .orderByAsc(Resource::getSortNo);
            List<Resource> resourceChildren = resourceMapper.selectList(queryWrapper);
            if (resourceChildren.size()>0){
                menuVo.setRedirect("/"+resource.getResourceName()+"/"+resourceChildren.get(0).getResourceName());
                List<MenuVo> listChildren  = new ArrayList<>();
                this.recursionMenuVo(listChildren,resourceChildren,resource.getRequestPath());
                menuVo.setChildren(listChildren);
            }
            list.add(menuVo);
        }
        return list;
    }
}
