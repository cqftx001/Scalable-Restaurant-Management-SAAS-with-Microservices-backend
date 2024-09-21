package com.itheima.restkeeper;

import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.PlacesVo;

import java.util.List;

/**
 * @ClassName PlacesFace.java
 * @Description 地方服务
 */
public interface PlacesFace {

    /***
     * @description 查询下级
     *
     * @param parentId
     * @return
     * @return: java.util.List<com.itheima.restkeeper.req.PlacesVo>
     */
    public List<PlacesVo> findPlacesVoListByParentId(Long parentId)throws ProjectException;
}
