package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * @ClassName TradingVo.java
 * @Description 交易结果
 */
@Data
@NoArgsConstructor
public class TradingVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public TradingVo(Long id,Long productOrderNo,Long tradingOrderNo,String tradingChannel,
                     String tradingType,String tradingState,String payeeName,Long payeeId,String payerName,
                     Long payerId,BigDecimal tradingAmount,BigDecimal refund,String isRefund,String resultCode,String resultMsg,
                     String resultJson,String placeOrderCode,String placeOrderMsg,String placeOrderJson,Long enterpriseId,
                     Long storeId,String memo,String qrCodeUrl,BigDecimal operTionRefund){
        super(id);
        this.productOrderNo=productOrderNo;
        this.tradingOrderNo=tradingOrderNo;
        this.tradingChannel=tradingChannel;
        this.tradingType=tradingType;
        this.tradingState=tradingState;
        this.payeeName=payeeName;
        this.payeeId=payeeId;
        this.payerName=payerName;
        this.payerId=payerId;
        this.tradingAmount=tradingAmount;
        this.refund=refund;
        this.isRefund=isRefund;
        this.resultCode=resultCode;
        this.resultMsg=resultMsg;
        this.resultJson=resultJson;
        this.placeOrderCode=placeOrderCode;
        this.placeOrderMsg=placeOrderMsg;
        this.placeOrderJson=placeOrderJson;
        this.enterpriseId=enterpriseId;
        this.storeId=storeId;
        this.memo=memo;
        this.qrCodeUrl=qrCodeUrl;
        this.operTionRefund = operTionRefund;
    }

    @ApiModelProperty(value = "业务系统订单号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productOrderNo;

    @ApiModelProperty(value = "交易系统订单号【对于三方来说：商户订单】")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tradingOrderNo;

    @ApiModelProperty(value = "支付渠道【支付宝、微信、现金、免单挂账】")
    private String tradingChannel;

    @ApiModelProperty(value = "交易类型【付款、退款、免单、挂账】")
    private String tradingType;

    @ApiModelProperty(value = "交易单状态【DFK待付款,FKZ付款中,QXDD取消订单,YJS已结算,MD免单,GZ挂账】")
    private String tradingState;

    @ApiModelProperty(value = "收款人姓名")
    private String payeeName;

    @ApiModelProperty(value = "收款人账户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long payeeId;

    @ApiModelProperty(value = "付款人姓名")
    private String payerName;

    @ApiModelProperty(value = "付款人Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long payerId;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradingAmount;

    @ApiModelProperty(value = "退款金额【付款后】")
    private BigDecimal refund;

    @ApiModelProperty(value = "是否有退款：YES，NO")
    private String isRefund;

    @ApiModelProperty(value = "第三方交易返回编码【最终确认交易结果】")
    private String resultCode;

    @ApiModelProperty(value = "第三方交易返回提示消息【最终确认交易信息】")
    private String resultMsg;

    @ApiModelProperty(value = "第三方交易返回信息json【分析交易最终信息】")
    private String resultJson;

    @ApiModelProperty(value = "统一下单返回编码")
    private String placeOrderCode;

    @ApiModelProperty(value = "统一下单返回信息")
    private String placeOrderMsg;

    @ApiModelProperty(value = "统一下单返回信息json【用于生产二维码、Android ios唤醒支付等】")
    private String placeOrderJson;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "门店主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long storeId;

    @ApiModelProperty(value = "备注【订单门店，桌台信息】")
    private String memo;

    @ApiModelProperty(value = "二维码路径")
    private String qrCodeUrl;

    @ApiModelProperty(value = "退款请求号")
    private String outRequestNo;

    @ApiModelProperty(value = "操作退款金额")
    private BigDecimal operTionRefund;
}
