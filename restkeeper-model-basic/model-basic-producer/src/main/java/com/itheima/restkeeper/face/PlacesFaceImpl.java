package com.itheima.restkeeper.face;

import com.itheima.restkeeper.PlacesFace;
import com.itheima.restkeeper.enums.PlacesEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.PlacesVo;
import com.itheima.restkeeper.service.IPlacesService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName PlacesFaceImpl.java
 * @Description 地方表 服务类
 */
@DubboService(version = "${dubbo.application.version}",retries = 0,timeout = 5000)
@Slf4j
public class PlacesFaceImpl implements PlacesFace {

    @Autowired
    IPlacesService placesService;

    @Override
    public List<PlacesVo> findPlacesVoListByParentId(Long parentId)throws ProjectException {
        try {
            return BeanConv.toBeanList(placesService.
                    findPlacesVoListByParentId(parentId),PlacesVo.class);
        } catch (Exception e) {
            log.error("查询查询省市区异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PlacesEnum.PAGE_FAIL);
        }

    }
}
