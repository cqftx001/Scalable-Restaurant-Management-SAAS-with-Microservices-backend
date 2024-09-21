package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.restkeeper.pojo.Places;
import com.itheima.restkeeper.mapper.PlacesMapper;
import com.itheima.restkeeper.req.PlacesVo;
import com.itheima.restkeeper.service.IPlacesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：地方表 服务实现类
 */
@Service
public class PlacesServiceImpl extends ServiceImpl<PlacesMapper, Places> implements IPlacesService {

    @Override
    public List<Places> findPlacesVoListByParentId(Long parentId) {
        QueryWrapper<Places> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Places::getParentId,parentId);
        return list(queryWrapper);
    }
}
