package com.itheima.restkeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.mapper.EnterpriseMapper;
import com.itheima.restkeeper.pojo.Enterprise;
import com.itheima.restkeeper.req.EnterpriseVo;
import com.itheima.restkeeper.service.IEnterpriseService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description：企业账号管理 服务实现类
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

    @Override
    public Enterprise findEnterpriseVoByEnterpriseId(Long enterpriseId) {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Enterprise::getEnterpriseId,enterpriseId);
        return getOne(queryWrapper);
    }

    @Override
    public Page<Enterprise> findEnterpriseVoPage(EnterpriseVo enterpriseVo, int pageNum, int pageSize) {
        Page<Enterprise> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        if (!EmptyUtil.isNullOrEmpty(enterpriseVo.getEnterpriseName())) {
            queryWrapper.lambda().likeRight(Enterprise::getEnterpriseName,enterpriseVo.getEnterpriseName());
        }
        if (!EmptyUtil.isNullOrEmpty(enterpriseVo.getEnterpriseNo())) {
            queryWrapper.lambda().eq(Enterprise::getEnterpriseNo,enterpriseVo.getEnterpriseNo());
        }
        if (!EmptyUtil.isNullOrEmpty(enterpriseVo.getStatus())) {
            queryWrapper.lambda().eq(Enterprise::getStatus,enterpriseVo.getStatus());
        }
        if (!EmptyUtil.isNullOrEmpty(enterpriseVo.getEnableFlag())) {
            queryWrapper.lambda().eq(Enterprise::getEnableFlag,enterpriseVo.getEnableFlag());
        }
        queryWrapper.lambda().orderByDesc(Enterprise::getCreatedTime);
        return page(page, queryWrapper);
    }

    @Override
    public Enterprise createEnterprise(EnterpriseVo EnterpriseVo) {
        Enterprise enterprise = BeanConv.toBean(EnterpriseVo, Enterprise.class);
        boolean flag = save(enterprise);
        if (flag){
            return enterprise;
        }
        return null;
    }

    @Override
    public Boolean updateEnterprise(EnterpriseVo EnterpriseVo) {
        Enterprise enterprise = BeanConv.toBean(EnterpriseVo, Enterprise.class);
        return updateById(enterprise);
    }

    @Override
    public Boolean deleteEnterprise(String[] checkedIds) {
        List<String> ids = Arrays.asList(checkedIds);
        List<Long> idsLong = new ArrayList<>();
        ids.forEach(n -> {
            idsLong.add(Long.valueOf(n));
        });
        return removeByIds(idsLong);
    }

    @Override
    public List<Enterprise> initEnterpriseIdOptions() {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Enterprise::getEnableFlag,SuperConstant.YES);
        return list(queryWrapper);
    }
}
