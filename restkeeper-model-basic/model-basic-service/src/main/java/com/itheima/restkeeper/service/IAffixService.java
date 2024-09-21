package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.Affix;
import com.itheima.restkeeper.req.AffixVo;
import com.itheima.restkeeper.req.UploadMultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description：附件 服务类
 */
public interface IAffixService extends IService<Affix> {

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
    Affix upLoad(UploadMultipartFile multipartFile,
                 AffixVo affixVo) throws ProjectException;

    /**
     * @Description 为上传绑定对应的业务Id
     * @param  affixVo 附件对象
     * @return
     */
    Affix bindBusinessId(AffixVo affixVo);


    /**
     * @Description 批量上传绑定对应的业务Id
     * @param  affixVos 附件对象
     * @return
     */
    List<Affix> bindBatchBusinessId(List<AffixVo> affixVos);

    /**
     * @Description 按业务ID查询附件
     * @param  businessId 附件对象业务Id
     * @return
     */
    List<Affix> findAffixVoByBusinessId(Long businessId);

    /**
     * @Description 删除业务相关附件
     * @param businessId 业务Id
     * @return
     */
    Boolean deleteAffixVoByBusinessId(Long businessId);

    /**
     * @Description 图片列表
     * @param affixVo 查询条件
     * @return
     */
    Page<Affix> findAffixVoPage(AffixVo affixVo, int pageNum, int pageSize);

    /**
     * @Description 删除图片
     * @param checkedIds 对象信息Id
     * @return
     */
    Boolean deleteAffix(String[] checkedIds);


}
