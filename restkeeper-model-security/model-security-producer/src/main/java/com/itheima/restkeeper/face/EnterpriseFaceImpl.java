package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.EnterpriseFace;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.EnterpriseEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.init.InitEnterpriseSite;
import com.itheima.restkeeper.pojo.Enterprise;
import com.itheima.restkeeper.req.EnterpriseVo;
import com.itheima.restkeeper.service.IEnterpriseService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName EnterpriseFaceImpl.java
 * @Description 企业服务
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
        methods ={
                @Method(name = "findEnterpriseVoPage",retries = 2),
                @Method(name = "createEnterprise",retries = 0),
                @Method(name = "updateEnterprise",retries = 0),
                @Method(name = "deleteEnterprise",retries = 0)
        })
public class EnterpriseFaceImpl implements EnterpriseFace {

    @Autowired
    IEnterpriseService enterpriseService;

    @Autowired
    InitEnterpriseSite initEnterpriseWebSite;

    @Override
    public Page<EnterpriseVo> findEnterpriseVoPage(EnterpriseVo enterpriseVo,
                                                   int pageNum,
                                                   int pageSize)throws ProjectException {
        try {
            Page<Enterprise> page = enterpriseService.findEnterpriseVoPage(enterpriseVo, pageNum, pageSize);
            Page<EnterpriseVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<Enterprise> enterpriseList = page.getRecords();
            List<EnterpriseVo> enterpriseVoList = BeanConv.toBeanList(enterpriseList,EnterpriseVo.class);
            pageVo.setRecords(enterpriseVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询企业列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(EnterpriseEnum.PAGE_FAIL);
        }
    }

    @Override
    public EnterpriseVo createEnterprise(EnterpriseVo eterperiseVo)throws ProjectException {
        try {
            Enterprise enterpriseResult = enterpriseService.createEnterprise(eterperiseVo);
            //同步缓存
            if (!EmptyUtil.isNullOrEmpty(enterpriseResult)){
                initEnterpriseWebSite.addWebSiteforRedis(eterperiseVo);
            }
            return BeanConv.toBean(enterpriseResult,EnterpriseVo.class);
        } catch (Exception e) {
            log.error("保存企业异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(EnterpriseEnum.CREATE_FAIL);
        }

    }

    @Override
    public Boolean updateEnterprise(EnterpriseVo enterpriseVo)throws ProjectException {
        try {
            Boolean flag = enterpriseService.updateEnterprise(enterpriseVo);
            //同步缓存
            if (flag){
                if (enterpriseVo.getEnableFlag().equals(SuperConstant.YES)){
                    initEnterpriseWebSite.updataWebSiteforRedis(enterpriseVo);
                }else {
                    initEnterpriseWebSite.deleteWebSiteforRedis(enterpriseVo);
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("修改企业异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(EnterpriseEnum.UPDATE_FAIL);
        }

    }

    @Override
    public Boolean deleteEnterprise(String[] checkedIds)throws ProjectException {
        try {
            //同步缓存
            for (String checkedId : checkedIds) {
                Enterprise enterprise = enterpriseService.getById(checkedId);
                EnterpriseVo enterpriseVo = BeanConv.toBean(enterprise, EnterpriseVo.class);
                initEnterpriseWebSite.deleteWebSiteforRedis(enterpriseVo);
            }
            return  enterpriseService.deleteEnterprise(checkedIds);
        } catch (Exception e) {
            log.error("删除企业异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(EnterpriseEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<EnterpriseVo> initEnterpriseIdOptions() throws ProjectException{
        try {
            List<Enterprise> enterprises = enterpriseService.initEnterpriseIdOptions();
            return BeanConv.toBeanList(enterprises,EnterpriseVo.class);
        } catch (Exception e) {
            log.error("删除企业异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(EnterpriseEnum.INIT_ENTERPRISEID_OPTIONS_FAIL);
        }

    }


}
