package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.AffixFace;
import com.itheima.restkeeper.SmsSignFace;
import com.itheima.restkeeper.adapter.SmsSignAdapter;
import com.itheima.restkeeper.enums.SmsChannelEnum;
import com.itheima.restkeeper.enums.SmsSignEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.SmsSign;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.SmsSignVo;
import com.itheima.restkeeper.service.ISmsSignService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SmsSignFaceImpl.java
 * @Description TODO
 */
@Slf4j
@DubboService(version = "${dubbo.application.version}",timeout = 5000,
    methods ={
        @Method(name = "findSmsSignVoPage",retries = 2),
        @Method(name = "addSmsSign",retries = 0),
        @Method(name = "deleteSmsSign",retries = 0),
        @Method(name = "modifySmsSign",retries = 0),
        @Method(name = "updateSmsSignEnableFlag",retries = 0),
        @Method(name = "querySmsSign",retries = 2)
    })
public class SmsSignFaceImpl implements SmsSignFace {

    @Autowired
    SmsSignAdapter smsSignAdapter;

    @Autowired
    ISmsSignService smsSignService;

    @Autowired
    AffixFace affixFace;

    @Override
    public Page<SmsSignVo> findSmsSignVoPage(SmsSignVo smsSignVo,
                                             int pageNum,
                                             int pageSize) throws ProjectException{
        try {
            //查询Page<SmsSignVo>图片分页
            Page<SmsSign> page = smsSignService.findSmsSignVoPage(smsSignVo, pageNum, pageSize);
            //转化Page<SmsSignVo>为Page<SmsSignVo>
            Page<SmsSignVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //转换List<SmsSignVo>为 List<SmsSignVo>
            List<SmsSign> smsSignVoList = page.getRecords();
            List<SmsSignVo> smsSignVoVoList = BeanConv.toBeanList(smsSignVoList,SmsSignVo.class);
            for (SmsSignVo signVo : smsSignVoVoList) {
                List<AffixVo> affixVos = affixFace.findAffixVoByBusinessId(signVo.getId());
                LinkedList<AffixVo> linkedList = new LinkedList<>();
                if (!EmptyUtil.isNullOrEmpty(affixVos)){
                    linkedList.addAll(affixVos);
                }
                signVo.setAffixVos(linkedList);
            }
            pageVo.setRecords(smsSignVoVoList);
            //返回结果
            return pageVo;
        } catch (Exception e) {
            log.error("查询签名列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.PAGE_FAIL);
        }
    }

    @Override
    @Transactional
    public SmsSignVo addSmsSign(SmsSignVo smsSignVo) throws ProjectException{
        try {
            LinkedList<AffixVo> affixVos = smsSignVo.getAffixVos();
            StringBuffer base64Image =new StringBuffer();
            StringBuffer suffix =new StringBuffer();
            for (AffixVo affixVo : affixVos) {
                AffixVo affixVoHandler = affixFace.downLoad(affixVo.getId());
                base64Image.append(affixVoHandler.getBase64Image()).append("@");
                suffix.append(affixVoHandler.getSuffix()).append("@");
            }
            if (!EmptyUtil.isNullOrEmpty(base64Image)&&!EmptyUtil.isNullOrEmpty(suffix)){
                String base64ImageString = base64Image.toString();
                String suffixString = suffix.toString();
                smsSignVo.setProofImage(base64ImageString.substring(0,base64ImageString.length()-1));
                smsSignVo.setProofType(suffixString.substring(0,suffixString.length()-1));
            }
            SmsSignVo smsSignVoHandler = smsSignAdapter.addSmsSign(smsSignVo);
            if (!EmptyUtil.isNullOrEmpty(smsSignVoHandler)){
                List<AffixVo> affixVosHandler = affixVos.stream().map(n -> new AffixVo(
                        n.getId(),
                        smsSignVoHandler.getId())).collect(Collectors.toList());
                affixFace.bindBatchBusinessId(affixVosHandler);
            }
            return smsSignVo;
        } catch (Exception e) {
            log.error("添加签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.CREATE_FAIL);
        }
    }

    @Override
    @Transactional
    public Boolean deleteSmsSign(String[] checkedIds)throws ProjectException {
        try {
            Boolean flag = smsSignAdapter.deleteSmsSign(checkedIds);
            if (flag){
                for (String checkedId : checkedIds) {
                    affixFace.deleteAffixVoByBusinessId(Long.valueOf(checkedId));
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("删除签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.DELETE_FAIL);
        }
    }

    @Override
    @Transactional
    public Boolean modifySmsSign(SmsSignVo smsSignVo)throws ProjectException {
        try {
            LinkedList<AffixVo> affixVos = smsSignVo.getAffixVos();
            StringBuffer base64Image =new StringBuffer();
            StringBuffer suffix =new StringBuffer();
            for (AffixVo affixVo : affixVos) {
                AffixVo affixVoHandler = affixFace.downLoad(affixVo.getId());
                base64Image.append(affixVoHandler.getBase64Image()).append("@");
                suffix.append(affixVoHandler.getSuffix()).append("@");
            }
            if (!EmptyUtil.isNullOrEmpty(base64Image)&&!EmptyUtil.isNullOrEmpty(suffix)){
                String base64ImageString = base64Image.toString();
                String suffixString = suffix.toString();
                smsSignVo.setProofImage(base64ImageString.substring(0,base64ImageString.length()-1));
                smsSignVo.setProofType(suffixString.substring(0,suffixString.length()-1));
            }
            Boolean flag = smsSignAdapter.modifySmsSign(smsSignVo);
            if (flag){
                affixFace.deleteAffixVoByBusinessId(smsSignVo.getId());
                List<AffixVo> affixVosHandler = affixVos.stream().map(n -> new AffixVo(
                        n.getId(),
                        smsSignVo.getId())).collect(Collectors.toList());
                affixFace.bindBatchBusinessId(affixVosHandler);
            }
            return flag;
        } catch (Exception e) {
            log.error("修改签名异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean updateSmsSignEnableFlag(SmsSignVo smsSignVo) throws ProjectException {
        try {
            return smsSignService.updateById(BeanConv.toBean(smsSignVo,SmsSign.class));
        } catch (Exception e) {
            log.error("修改签名状态异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.UPDATE_FAIL);
        }
    }

    @Override
    public Boolean querySmsSign(SmsSignVo smsSignVo) throws ProjectException{
        try {
            return smsSignAdapter.querySmsSign(smsSignVo);
        } catch (Exception e) {
            log.error("查询签名状态异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
    }

    @Override
    public List<SmsSignVo> findSmsSignVoList()throws ProjectException {
        try {
            return BeanConv.toBeanList(smsSignService.findSmsSignVoList(), SmsSignVo.class);
        } catch (Exception e) {
            log.error("查找所有通道异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsChannelEnum.SELECT_FAIL);
        }
    }
}
