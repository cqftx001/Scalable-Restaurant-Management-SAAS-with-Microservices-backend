package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.EnterpriseVo;

import java.util.List;

/**
 * @Description：企业门店服务接口
 */
public interface EnterpriseFace {

    /**
     * @Description 企业门店列表
     * @param enterpriseVo 查询条件
     * @return
     */
    Page<EnterpriseVo> findEnterpriseVoPage(EnterpriseVo enterpriseVo,
                                            int pageNum,
                                            int pageSize)throws ProjectException;

    /**
     * @Description 创建企业门店
     * @param enterpriseVo 对象信息
     * @return
     */
    EnterpriseVo createEnterprise(EnterpriseVo enterpriseVo)throws ProjectException;

    /**
     * @Description 修改企业门店
     * @param enterpriseVo 对象信息
     * @return
     */
    Boolean updateEnterprise(EnterpriseVo enterpriseVo)throws ProjectException;

    /**
     * @Description 删除企业门店
     * @param checkedIds 选择IDS
     * @return
     */
    Boolean deleteEnterprise(String[] checkedIds)throws ProjectException;

    /**
     * @Description 初始化企业下拉框
     * @return
     */
    List<EnterpriseVo> initEnterpriseIdOptions()throws ProjectException;

}
