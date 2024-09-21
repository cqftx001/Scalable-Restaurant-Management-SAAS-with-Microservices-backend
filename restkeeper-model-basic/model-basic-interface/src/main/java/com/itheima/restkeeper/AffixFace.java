package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.UploadMultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName AffixFace.java
 * @Description 附件接口
 */
public interface AffixFace {

    /***
     * @description 下载文件
     * @param affixId
     * @return
     */
    AffixVo downLoad(Long affixId) throws ProjectException;

    /**
     * @Description 文件上传
     * @param multipartFile 上传对象
     * @param affixVo 附件对象
     * @return
     */
    AffixVo upLoad(UploadMultipartFile multipartFile, AffixVo affixVo) throws ProjectException;

    /**
     * @Description 为上传绑定对应的业务Id
     * @param  affixVo 附件对象
     * @return
     */
    AffixVo bindBusinessId(AffixVo affixVo) throws ProjectException;

    /**
     * @Description 批量上传绑定对应的业务Id
     * @param  affixVos 附件对象
     * @return
     */
    List<AffixVo> bindBatchBusinessId(List<AffixVo> affixVos) throws ProjectException;

    /**
     * @Description 按业务ID查询附件
     * @param  businessId 附件对象业务Id
     * @return
     */
    List<AffixVo> findAffixVoByBusinessId(Long businessId) throws ProjectException;

    /**
     * @Description 删除业务相关附件
     * @param businessId 业务Id
     * @return
             */
    Boolean deleteAffixVoByBusinessId(Long businessId) throws ProjectException;

    /**
     * @Description 图片列表
     * @param affixVo 查询条件
     * @return
     */
    Page<AffixVo> findAffixVoPage(AffixVo affixVo, int pageNum, int pageSize) throws ProjectException;

    /**
     * @Description 删除图片
     * @param checkedIds 对象信息Id
     * @return
     */
    Boolean deleteAffix(String[] checkedIds) throws ProjectException;

}
