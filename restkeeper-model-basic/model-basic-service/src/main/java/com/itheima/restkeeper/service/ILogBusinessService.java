package com.itheima.restkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.pojo.LogBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.restkeeper.req.LogBusinessVo;

/**
 * @Description： 服务类
 */
public interface ILogBusinessService extends IService<LogBusiness> {

    /**
     * @Description 日志列表
     * @param logBusinessVo 查询条件
     * @return
     */
    Page<LogBusiness> findLogBusinessVoPage(LogBusinessVo logBusinessVo, int pageNum, int pageSize);
}
