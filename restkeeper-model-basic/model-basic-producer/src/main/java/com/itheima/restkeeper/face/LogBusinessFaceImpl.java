package com.itheima.restkeeper.face;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.restkeeper.LogBusinessFace;
import com.itheima.restkeeper.enums.LogBusinessEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.pojo.LogBusiness;
import com.itheima.restkeeper.req.LogBusinessVo;
import com.itheima.restkeeper.service.ILogBusinessService;
import com.itheima.restkeeper.utils.BeanConv;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import com.itheima.restkeeper.utils.ResponseWrapBuild;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description：日志查询
 */
@DubboService(version = "${dubbo.application.version}",retries = 0,timeout = 5000)
@Slf4j
public class LogBusinessFaceImpl implements LogBusinessFace {

    @Autowired
    ILogBusinessService logBusinessService;

    @Override
    public Page<LogBusinessVo> findLogBusinessVoPage(LogBusinessVo logBusinessVo,
                                                     int pageNum,
                                                     int pageSize) throws ProjectException{
        try {
            Page<LogBusiness> page = logBusinessService.findLogBusinessVoPage(logBusinessVo, pageNum, pageSize);
            Page<LogBusinessVo> pageVo = new Page<>();
            BeanConv.toBean(page,pageVo);
            //结果集转换
            List<LogBusiness> routeList = page.getRecords();
            List<LogBusinessVo> routeVoList = BeanConv.toBeanList(routeList,LogBusinessVo.class);
            pageVo.setRecords(routeVoList);
            return pageVo;
        } catch (Exception e) {
            log.error("查询日志列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(LogBusinessEnum.PAGE_FAIL);
        }

    }
}
