package com.itheima.restkeeper.listen;

import com.itheima.restkeeper.NativePayFace;
import com.itheima.restkeeper.OrderFace;
import com.itheima.restkeeper.TradingFace;
import com.itheima.restkeeper.constant.TradingConstant;
import com.itheima.restkeeper.req.TradingVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description：NativePay付款结果同步
 */
@Component
public class NativePayHandlerJob {

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
    @XxlJob(value = "nativePayHandlerJob")
    @GlobalTransactional
    public ReturnT<String> execute(String param) {
        //查询所有支付中的订单
        List<TradingVo> tradingVoList = tradingFace.findTradingByTradingState(TradingConstant.FKZ);
        for (TradingVo tradingVo : tradingVoList) {
            this.synchTradingState(tradingVo);
        }
        ReturnT.SUCCESS.setMsg("执行-支付同步-成功");
        return ReturnT.SUCCESS;

    }

    public void synchTradingState(TradingVo tradingVo){
        //交易单状态查询
        TradingVo tradingVoResult = nativePayFace.queryDownLineTrading(tradingVo);
        //订单状态同步交易单状态
        if (!EmptyUtil.isNullOrEmpty(tradingVoResult)){
            Long productOrderNo = tradingVoResult.getProductOrderNo();
            String tradingState = tradingVoResult.getTradingState();
            orderFace.synchTradingState(productOrderNo,tradingState);
        }
    }

}
