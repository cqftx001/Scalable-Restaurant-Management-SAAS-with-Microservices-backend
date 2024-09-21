package com.itheima.restkeeper.handler.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName RefundResponse.java
 * @Description 退款返回
 */
@Data
@NoArgsConstructor
public class RefundResponse {

    //编码定义
    private String code;

    //商户退款单号
    @JSONField(name = "outRefundNo")
    private String out_refund_no;

    //商户订单号
    @JSONField(name = "outTradeNo")
    private String out_trade_no;

    //退款状态
    //SUCCESS：退款成功
    //CLOSED：退款关闭
    //PROCESSING：退款处理中
    //ABNORMAL：退款异常
    private String status;

    //金额信息
    private AmountResponse amount;
}
