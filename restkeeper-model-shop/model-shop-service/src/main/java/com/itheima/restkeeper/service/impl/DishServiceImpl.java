package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.Dish;
import com.itheima.restkeeper.mapper.DishMapper;
import com.itheima.restkeeper.pojo.DishFlavor;
import com.itheima.restkeeper.req.DishVo;
import com.itheima.restkeeper.service.IDishFlavorService;
import com.itheima.restkeeper.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：菜品管理 服务实现类
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    IDishFlavorService dishFlavorService;

    @Autowired
    DishMapper dishMapper;

    @Override
    public Page<Dish> findDishVoPage(DishVo dishVo, int pageNum, int pageSize) {
        //构建Page<Dish>分页对象
        Page<Dish> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        //按分类查询
        if (!EmptyUtil.isNullOrEmpty(dishVo.getCategoryId())) {
            queryWrapper.lambda().eq(Dish::getCategoryId,dishVo.getCategoryId());
        }
        //按菜品名称查询
        if (!EmptyUtil.isNullOrEmpty(dishVo.getDishName())) {
            queryWrapper.lambda().likeRight(Dish::getDishName,dishVo.getDishName());
        }
        //按简码查询
        if (!EmptyUtil.isNullOrEmpty(dishVo.getCode())) {
            queryWrapper.lambda().likeRight(Dish::getCode,dishVo.getCode());
        }
        //按有效性查询
        if (!EmptyUtil.isNullOrEmpty(dishVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Dish::getEnableFlag,dishVo.getEnableFlag());
        }
        //按sortNo升序
        queryWrapper.lambda().orderByAsc(Dish::getSortNo);
        //执行page返回结果
        return page(page, queryWrapper);
    }

    @Override
    @Transactional
    public Dish createDish(DishVo dishVo) {
        //转换DishVo为Dish
        Dish dish = BeanConv.toBean(dishVo, Dish.class);
        //执行保存
        boolean flag = save(dish);
        //保存菜品和口味中间表信息
        if (flag){
            List<DishFlavor> list = Lists.newArrayList();
            List<String> dataKeys = Arrays.asList(dishVo.getHasDishFlavor());
            dataKeys.forEach(n->{
                DishFlavor dishFlavor = DishFlavor.builder()
                        .dishId(dish.getId())
                        .dataKey(n)
                        .build();
                list.add(dishFlavor);
            });
            flag = dishFlavorService.saveBatch(list);
        }
        if (flag){
            return dish;
        }
        return null;
    }

    /**
     * @Description 菜品拥有的口味
     */
    private List<String> dishHasDishFlavor(Long dishId){
        QueryWrapper<DishFlavor> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DishFlavor::getDishId,dishId);
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        ArrayList<String> listDataKeys = Lists.newArrayList();
        if (!EmptyUtil.isNullOrEmpty(list)){
            list.forEach(n->{
                listDataKeys.add(String.valueOf(n.getDataKey()));
            });
        }
        return listDataKeys;
    }

    @Override
    public Boolean updateDish(DishVo dishVo) {
        Dish dish = BeanConv.toBean(dishVo, Dish.class);
        boolean flag = updateById(dish);
        if (flag){
            //删除以往有的口味
            QueryWrapper<DishFlavor> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DishFlavor::getDishId,dish.getId());
            dishFlavorService.remove(queryWrapper);
        }
        //添加新口味
        List<DishFlavor> list = Lists.newArrayList();
        List<String> newDishHasDishFlavors = Arrays.asList(dishVo.getHasDishFlavor());
        newDishHasDishFlavors.forEach(n->{
            DishFlavor dishFlavors = DishFlavor.builder()
                    .dishId(dish.getId())
                    .dataKey(n)
                    .build();
            list.add(dishFlavors);
        });
        flag = dishFlavorService.saveBatch(list);
        return flag;
    }

    @Override
    public Boolean deleteDish(String[] checkedIds) {
        //构建选择ids的List<String>
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        //批量移除菜品
        boolean flag = removeByIds(idsLong);
        //批量移除菜品口味
        if (flag){
            QueryWrapper<DishFlavor> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(DishFlavor::getDishId,idsLong);
            flag = dishFlavorService.remove(queryWrapper);
        }
        return flag;
    }

    @Override
    public List<Dish> findDishVoByCategoryId(Long categoryId) {
        //构建查询条件：菜品起售，菜品有效
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,categoryId)
                .eq(Dish::getEnableFlag,SuperConstant.YES)
                .eq(Dish::getDishStatus,SuperConstant.YES);
        //执行list查询
        return list(lambdaQueryWrapper);
    }

    @Override
    public List<Dish> findDishVoByStoreId(Long storeId) {
        //构建查询条件：店铺ID，菜品起售，菜品有效
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getStoreId,storeId)
                .eq(Dish::getEnableFlag,SuperConstant.YES)
                .eq(Dish::getDishStatus,SuperConstant.YES);
        //执行list查询
        return list(lambdaQueryWrapper);
    }

    @Override
    public Boolean updateDishNumber(Long step,Long dishId) {
        //修改菜品数量
        Integer row = dishMapper.updateDishNumber(step,dishId);
        return row==1;
    }

    @Override
    public List<Dish> findDishVos() {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dish::getEnableFlag,SuperConstant.YES)
                .eq(Dish::getDishStatus,SuperConstant.YES);
        return list(queryWrapper);
    }


}
