package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.basic.BasicPojo;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.pojo.Table;
import com.itheima.restkeeper.mapper.TableMapper;
import com.itheima.restkeeper.req.TableVo;
import com.itheima.restkeeper.service.ITableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：桌台 服务实现类
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements ITableService {

    @Override
    public Page<Table> findTableVoPage(TableVo tableVo,int pageNum,int pageSize) {
        //构建分页对象
        Page<Table> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Table> queryWrapper = new QueryWrapper<>();
        //按桌台名称查询
        if (!EmptyUtil.isNullOrEmpty(tableVo.getTableName())) {
            queryWrapper.lambda().likeRight(Table::getTableName,tableVo.getTableName());
        }
        //按就餐人数查询
        if (!EmptyUtil.isNullOrEmpty(tableVo.getTableSeatNumber())) {
            queryWrapper.lambda().eq(Table::getTableSeatNumber,tableVo.getTableSeatNumber());
        }
        //按桌台使用状态查询
        if (!EmptyUtil.isNullOrEmpty(tableVo.getTableStatus())) {
            queryWrapper.lambda().eq(Table::getTableStatus,tableVo.getTableStatus());
        }
        //按桌台有效性查询
        if (!EmptyUtil.isNullOrEmpty(tableVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Table::getEnableFlag,tableVo.getEnableFlag());
        }
        //按sortNo升序排列
        queryWrapper.lambda().orderByAsc(Table::getSortNo);
        //返回分页结果
        return page(page,queryWrapper);
    }

    @Override
    public Table createTable(TableVo tableVo) {
        //转换TableVo为Table
        Table table = BeanConv.toBean(tableVo, Table.class);
        //执行保存
        boolean flag = save(table);
        if (flag){
            return table;
        }
        return null;
    }

    @Override
    public Boolean updateTable(TableVo tableVo) {
        //转换TableVo为Table
        Table table = BeanConv.toBean(tableVo, Table.class);
        //执行按ID进行修改
        return updateById(table);
    }

    @Override
    public Boolean deleteTable(String[] checkedIds) {
        //构建选择ids的List<String>集合
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n->{
            idsLong.add(Long.valueOf(n));
        });
        //执行批量移除
        return removeByIds(idsLong);
    }

    @Override
    public List<Table> findTableVoList() {
        //构建查询条件：有效状态
        QueryWrapper<Table> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BasicPojo::getEnableFlag, SuperConstant.YES);
        //执行list查询
        return list(queryWrapper);
    }

    @Override
    public Boolean openTable(TableVo tableVo) {
        //构建条件：SuperConstant.FREE
        LambdaQueryWrapper<Table> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .eq(Table::getTableStatus,SuperConstant.FREE).eq(Table::getId,tableVo.getId());
        //执行update条件查询
        return update(BeanConv.toBean(tableVo,Table.class),lambdaQueryWrapper);
    }
}
