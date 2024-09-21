package com.itheima.restkeeper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.req.LogBusinessVo;

/**
 * @Description：文件服务接口
 */
public interface LogBusinessFace {


    /**
     * @Description 日志列表
     * @param logBusinessVo 查询条件
     * @return
     */
    Page<LogBusinessVo> findLogBusinessVoPage(LogBusinessVo logBusinessVo,
                                              int pageNum,
                                              int pageSize) throws ProjectException;

}
