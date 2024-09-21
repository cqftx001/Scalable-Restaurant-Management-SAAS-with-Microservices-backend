package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.Enterprise;
import com.itheima.restkeeper.req.EnterpriseVo;

import java.util.List;

/**
 * @Description：企业账号管理 服务类
 */
public interface IEnterpriseService extends IService<Enterprise> {

    /***
     * @description 按企业号查找企业
     * @return
     * @return:
     */
    Enterprise findEnterpriseVoByEnterpriseId(Long EnterpriseId);

    /**
     * @Description 企业列表
     * @param enterpriseVo 查询条件
     * @return
     */
    Page<Enterprise> findEnterpriseVoPage(EnterpriseVo enterpriseVo, int pageNum, int pageSize);

    /**
     * @Description 创建企业
     * @param enterpriseVo 对象信息
     * @return
     */
    Enterprise createEnterprise(EnterpriseVo enterpriseVo);

    /**
     * @Description 修改企业
     * @param enterpriseVo 对象信息
     * @return
     */
    Boolean updateEnterprise(EnterpriseVo enterpriseVo);

    /**
     * @Description 删除企业
     * @param checkedIds 选择IDS
     * @return
     */
    Boolean deleteEnterprise(String[] checkedIds);

    /**
     * @Description 初始化企业下拉框
     * @return
     */
    List<Enterprise> initEnterpriseIdOptions();
}
