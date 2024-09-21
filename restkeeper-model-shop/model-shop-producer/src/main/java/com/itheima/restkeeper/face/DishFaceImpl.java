package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.DishFace;
import com.itheima.restkeeper.constant.AppletCacheConstant;
import com.itheima.restkeeper.enums.DishEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Dish;
import com.itheima.restkeeper.pojo.DishFlavor;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.DishVo;
import com.itheima.restkeeper.service.IDishFlavorService;
import com.itheima.restkeeper.service.IDishService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName DishFaceImpl.java
 * @Description 菜品接口实现
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
        methods ={
                @Method(name = "findDishVoPage",retries = 2),
                @Method(name = "createDish",retries = 0),
                @Method(name = "updateDish",retries = 0),
                @Method(name = "deleteDish",retries = 0)
        })
public class DishFaceImpl implements DishFace {

    @Autowired
    IDishService dishService;

    @Autowired
    IDishFlavorService dishFlavorService;

    @Autowired
    RedissonClient redissonClient;

    @DubboReference(version = "${dubbo.application.version}",check = false)
    AffixFace affixFace;

    @Override
    public Page<DishVo> findDishVoPage(DishVo dishVo,
                                       int pageNum,
                                       int pageSize)throws ProjectException {
        try {
            //查询菜品分页
            Page<Dish> page = dishService.findDishVoPage(dishVo, pageNum, pageSize);
            Page<DishVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Dish> dishList = page.getRecords();
            List<DishVo> dishVoList = BeanConv.toBeanList(dishList,DishVo.class);
            if (!EmptyUtil.isNullOrEmpty(dishVoList)){
                dishVoList.forEach(n->{
                    //处理附件
                    List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(n.getId());
                    if (!EmptyUtil.isNullOrEmpty(affixVoList)){
                        n.setAffixVo(affixVoList.get(0));
                    }
                    //处理口味
                    List<DishFlavor> dishFlavors = dishFlavorService.findDishFlavorByDishId(n.getId());
                    List<String> dishFavorList = new ArrayList<>();
                    for (DishFlavor dishFlavor : dishFlavors) {
                        dishFavorList.add(String.valueOf(dishFlavor.getDataKey()));
                    }
                    String[] dishFlavorDataKey = new String[dishFavorList.size()];
                    dishFavorList.toArray(dishFlavorDataKey);
                    n.setHasDishFlavor(dishFlavorDataKey);
                });
            }
            pageVo.setRecords(dishVoList);
            //返回结果
            return pageVo;
        } catch (Exception e) {
            log.error("查询菜品列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DishEnum.PAGE_FAIL);
        }
    }

    /**
     * 面试题：
     * 1、假设保存菜品成功，保存redis失败，问是否应该回滚菜品的数据？
     *  * 需要回滚，在点餐时菜品需要先在Redis中完成预扣，如果redis中没有当前的菜品，则无法完成预扣，菜无法售卖
     * 变化：假设保存的菜品的口味成功，保存到Redis失败，问是否应该回滚菜品分类的数据？
     *  * 不需要，保存redis不会影响正常业务逻辑的执行
     *
     * 2、更新数据时，应该是先更新redis还是先更新数据库？ 为什么？   双写一致性问题
     *  * 先更新数据库，考虑redis和mysql的数据时刻保持一致
     *
     */
    @Override
    @Transactional
    public DishVo createDish(DishVo dishVo) throws ProjectException{
        try {
            //创建菜品
            DishVo dishVoResult = BeanConv.toBean(dishService.createDish(dishVo), DishVo.class);
            dishVoResult.setHasDishFlavor(dishVo.getHasDishFlavor());
            if (EmptyUtil.isNullOrEmpty(dishVoResult.getId())) {
                throw new ProjectException(DishEnum.CREATE_FAIL);
            }
            //构建初始化库存****
            String key = AppletCacheConstant.REPERTORY_DISH+dishVoResult.getId();
            RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
            atomicLong.set(dishVoResult.getDishNumber());
            //绑定附件
            if (!EmptyUtil.isNullOrEmpty(dishVoResult)){
                affixFace.bindBusinessId(AffixVo.builder()
                        .businessId(dishVoResult.getId())
                        .id(dishVo.getAffixVo().getId())
                        .build());
            }
            dishVoResult.setAffixVo(AffixVo.builder()
                    .pathUrl(dishVo.getAffixVo().getPathUrl())
                    .businessId(dishVoResult.getId())
                    .id(dishVo.getAffixVo().getId()).build());
            return  dishVoResult;
        } catch (Exception e) {
            log.error("保存菜品异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DishEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateDish(DishVo dishVo) throws ProjectException {
        Boolean flag = false;
        //菜品库存锁，前段小程序点餐或后台修改订单项都需要加锁
        String keyLock = AppletCacheConstant.REPERTORY_DISH_LOCK+dishVo.getId();
        RLock lock = redissonClient.getLock(keyLock);
        //添加可重入锁
        try {
            if (lock.tryLock(
                AppletCacheConstant.REDIS_WAIT_TIME,
                AppletCacheConstant.REDIS_LEASETIME,
                TimeUnit.SECONDS)){
                //修改菜品
                flag = dishService.updateDish(dishVo);
                //处理菜品图片
                if (flag){
                    List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(dishVo.getId());
                    List<Long> affixIds = affixVoList.stream()
                            .map(AffixVo::getId)
                            .collect(Collectors.toList());
                    if (!affixIds.contains(dishVo.getAffixVo().getId())){
                        //删除图片
                        flag = affixFace.deleteAffixVoByBusinessId(dishVo.getId());
                        //绑定新图片
                        affixFace.bindBusinessId(AffixVo.builder()
                                .businessId(dishVo.getId())
                                .id(dishVo.getAffixVo().getId())
                                .build());
                    }
                }
                //构建redis库存
                String key = AppletCacheConstant.REPERTORY_DISH+dishVo.getId();
                RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
                atomicLong.set(dishVo.getDishNumber());
                return flag;
            }
        } catch (InterruptedException e) {
            log.warn("修改菜品产生并发dishId:{}",dishVo.getId());
           throw new ProjectException(DishEnum.UPDATE_FAIL);
        }finally {
            lock.unlock();
        }
        return flag;
    }

    @Override
    public Boolean deleteDish(String[] checkedIds)throws ProjectException {
        try {
            Boolean flag = dishService.deleteDish(checkedIds);
            for (String checkedId : checkedIds) {
                //删除菜品库存
                String key = AppletCacheConstant.REPERTORY_DISH+checkedId;
                RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
                atomicLong.delete();
                //删除图片
                affixFace.deleteAffixVoByBusinessId(Long.valueOf(checkedId));
            }
            return flag;
        } catch (Exception e) {
            log.error("删除菜品异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DishEnum.DELETE_FAIL);
        }
    }

    @Override
    public DishVo findDishByDishId(Long dishId)throws ProjectException {
        try {
            //按菜品ID查找菜品
            Dish dish = dishService.getById(dishId);
            if (!EmptyUtil.isNullOrEmpty(dish)){
                return BeanConv.toBean(dish,DishVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("查找菜品所有菜品异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DishEnum.SELECT_DISH_FAIL);
        }
    }

}
