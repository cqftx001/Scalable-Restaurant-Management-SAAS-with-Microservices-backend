package com.itheima.restkeeper.service;

import com.itheima.restkeeper.pojo.Places;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.PlacesVo;

import java.util.List;

/**
 * @ClassName IPlacesService.java
 * @Description：地方表 服务类
 */
public interface IPlacesService extends IService<Places> {

    /***
     * @description 查询下级
     *
     * @param parentId
     * @return
     * @return: java.util.List<com.itheima.restkeeper.req.PlacesVo>
     */
    public List<Places> findPlacesVoListByParentId(Long parentId);

}
