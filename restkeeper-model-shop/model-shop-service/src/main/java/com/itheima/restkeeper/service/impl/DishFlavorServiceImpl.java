package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.restkeeper.pojo.DishFlavor;
import com.itheima.restkeeper.mapper.DishFlavorMapper;
import com.itheima.restkeeper.service.IDishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description： 服务实现类
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {

    @Override
    public List<DishFlavor> findDishFlavorByDishId(Long dishId) {
        QueryWrapper<DishFlavor> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DishFlavor::getDishId,dishId);
        return list(queryWrapper);
    }
}
