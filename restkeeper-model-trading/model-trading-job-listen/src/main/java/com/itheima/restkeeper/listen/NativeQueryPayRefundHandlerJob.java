package com.itheima.restkeeper.listen;

import com.itheima.restkeeper.NativePayFace;
import com.itheima.restkeeper.OrderFace;
import com.itheima.restkeeper.TradingFace;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @Description：NativePay付款结果同步
 */
@Component
public class NativeQueryPayRefundHandlerJob {

    @DubboReference(version = "${dubbo.application.version}",check = false)
    NativePayFace nativePayFace;

    @DubboReference(version = "${dubbo.application.version}",check = false)
    TradingFace tradingFace;

    @DubboReference(version = "${dubbo.application.version}",check = false)
    OrderFace orderFace;

    /***
     * @description NativePay付款结果同步
     * @param param
     * @return
     */
    @XxlJob(value = "nativeQueryPayRefundHandlerJob")
    @GlobalTransactional
    public ReturnT<String> execute(String param) {
        //查询所有退款记录表所有状态不是 SUCCESS的数据
        //遍历列表
        //发起远程的查询退款的接口

        ReturnT.SUCCESS.setMsg("执行-支付同步-成功");
        return ReturnT.SUCCESS;

    }


}
