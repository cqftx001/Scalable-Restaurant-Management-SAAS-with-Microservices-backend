package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.BrandFace;
import com.itheima.restkeeper.enums.BrandEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Brand;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.BrandVo;
import com.itheima.restkeeper.service.IBrandService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BrandFaceImpl.java
 * @Description 品牌dubbo接口定义实现
 */
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findBrandVoPage",retries = 2),
        @Method(name = "createBrand",retries = 0),
        @Method(name = "updateBrand",retries = 0),
        @Method(name = "deleteBrand",retries = 0)
    })
@Slf4j
public class BrandFaceImpl implements BrandFace {

    @Autowired
    IBrandService brandService;

    @DubboReference(version = "${dubbo.application.version}",check = false, timeout = 9000000)
    AffixFace affixFace;

    @Override
    public Page<BrandVo> findBrandVoPage(BrandVo brandVo,
                                         int pageNum,
                                         int pageSize) throws ProjectException{
        try {
            //查询Page<Brand>品牌分页
            Page<Brand> page = brandService.findBrandVoPage(brandVo, pageNum, pageSize);
            //转化Page<Brand>为Page<BrandVo>
            Page<BrandVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //转换List<Brand>为 List<BrandVo>
            List<Brand> brandList = page.getRecords();
            List<BrandVo> brandVoList = BeanConv.toBeanList(brandList,BrandVo.class);
            //处理附件
            if (!EmptyUtil.isNullOrEmpty(pageVo)&&!EmptyUtil.isNullOrEmpty(brandVoList)){
                brandVoList.forEach(n->{
                    List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(n.getId());
                    if (!EmptyUtil.isNullOrEmpty(affixVoList)){
                        n.setAffixVo(affixVoList.get(0));
                    }
                });
            }

            //返回结果
            pageVo.setRecords(brandVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询品牌列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.PAGE_FAIL);
        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public BrandVo createBrand(BrandVo brandVo) throws ProjectException{
        try {
            Brand brand = brandService.createBrand(brandVo);
            BrandVo brandVoResult = BeanConv.toBean(brand, BrandVo.class);
            //绑定附件
            if (!EmptyUtil.isNullOrEmpty(brandVoResult)){
                affixFace.bindBusinessId(
                    AffixVo.builder()
                        .businessId(brandVoResult.getId())
                        .id(brandVo.getAffixVo().getId())
                        .build());
            }
            //回显
            brandVoResult.setAffixVo(AffixVo.builder()
                .pathUrl(brandVo.getAffixVo().getPathUrl())
                .businessId(brandVoResult.getId())
                .id(brandVo.getAffixVo().getId()).build());
            return brandVoResult;
        } catch (Exception e) {
            log.error("保存品牌异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.CREATE_FAIL);
        }
    }

    @Override
    public Boolean updateBrand(BrandVo brandVo)throws ProjectException {
        try {
            Boolean flag = brandService.updateBrand(brandVo);
            if (flag){
                List<AffixVo> affixVoList = affixFace.findAffixVoByBusinessId(brandVo.getId());
                List<Long> affixIds = affixVoList.stream().map(AffixVo::getId).collect(Collectors.toList());
                if (!affixIds.contains(brandVo.getAffixVo().getId())){
                    //删除图片
                    flag = affixFace.deleteAffixVoByBusinessId(brandVo.getId());
                    //绑定新图片
                    affixFace.bindBusinessId(AffixVo.builder()
                        .businessId(brandVo.getId())
                        .id(brandVo.getAffixVo().getId())
                        .build());
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("修改品牌列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.UPDATE_FAIL);
        }

    }

    @Override
    public Boolean deleteBrand(String[] checkedIds) throws ProjectException{
        try {
            Boolean flag = brandService.deleteBrand(checkedIds);
            //删除图片
            for (String checkedId : checkedIds) {
                affixFace.deleteAffixVoByBusinessId(Long.valueOf(checkedId));
            }
            return flag ;
        } catch (Exception e) {
            log.error("删除品牌列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.DELETE_FAIL);
        }
    }

    @Override
    public BrandVo findBrandByBrandId(Long brandId)throws ProjectException {
        try {
            Brand brand = brandService.getById(brandId);
            return BeanConv.toBean(brand,BrandVo.class);
        } catch (Exception e) {
            log.error("查找品牌所有品牌异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.SELECT_BRAND_FAIL);
        }
    }

    @Override
    public List<BrandVo> findBrandVoList()throws ProjectException {
        try {
            return BeanConv.toBeanList(brandService.findBrandVoList(),BrandVo.class);
        } catch (Exception e) {
            log.error("查询品牌列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(BrandEnum.PAGE_FAIL);
        }
    }
}
