package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.SmsBlacklistVo;

/**
 * @ClassName SmsBlacklistFace.java
 * @Description 黑名单dubbo接口
 */
public interface SmsBlacklistFace {

    /**
     * @Description 黑名单列表
     * @param smsBlacklistVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<SmsBlacklistVo>
     */
    Page<SmsBlacklistVo> findSmsBlacklistVoPage(SmsBlacklistVo smsBlacklistVo,
                                                int pageNum,
                                                int pageSize)throws ProjectException;

    /**
     * @Description 创建黑名单
     * @param smsBlacklistVo 对象信息
     * @return SmsBlacklistVo
     */
    SmsBlacklistVo createSmsBlacklist(SmsBlacklistVo smsBlacklistVo)throws ProjectException;

    /**
     * @Description 修改黑名单
     * @param smsBlacklistVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsBlacklist(SmsBlacklistVo smsBlacklistVo)throws ProjectException;

    /**
     * @Description 删除黑名单
     * @param checkedIds 选择中对象Ids
     * @return Boolean
     */
    Boolean deleteSmsBlacklist(String[] checkedIds)throws ProjectException;

    /**
     * @Description 查找黑名单
     * @param smsBlacklistId 选择对象信息Id
     * @return SmsBlacklistVo
     */
    SmsBlacklistVo findSmsBlacklistBySmsBlacklistId(Long smsBlacklistId)throws ProjectException;
    
}
