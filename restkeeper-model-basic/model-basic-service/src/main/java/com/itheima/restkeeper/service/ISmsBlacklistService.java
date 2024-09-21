package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.SmsBlacklist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.pojo.SmsBlacklist;
import com.itheima.restkeeper.req.SmsBlacklistVo;

/**
 * @Description：黑名单表 服务类
 */
public interface ISmsBlacklistService extends IService<SmsBlacklist> {

    /**
     * @Description 黑名单列表
     * @param smsBlacklistVo 查询条件
     * @param pageNum 当前页
     * @param pageSize 当前页
     * @return Page<SmsBlacklist>
     */
    Page<SmsBlacklist> findSmsBlacklistVoPage(
            SmsBlacklistVo smsBlacklistVo,
            int pageNum,
            int pageSize);

    /**
     * @Description 创建黑名单
     * @param smsBlacklistVo 对象信息
     * @return SmsBlacklist
     */
    SmsBlacklist createSmsBlacklist(SmsBlacklistVo smsBlacklistVo);

    /**
     * @Description 修改黑名单
     * @param smsBlacklistVo 对象信息
     * @return Boolean
     */
    Boolean updateSmsBlacklist(SmsBlacklistVo smsBlacklistVo);

    /**
     * @Description 删除黑名单
     * @param checkedIds 选择的黑名单ID
     * @return Boolean
     */
    Boolean deleteSmsBlacklist(String[] checkedIds);

}
