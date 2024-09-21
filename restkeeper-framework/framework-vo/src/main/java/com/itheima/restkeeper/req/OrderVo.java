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
import java.util.List;

/**
 * @ClassName OrderVo.java
 * @Description 订单
 */
@Data
@NoArgsConstructor
public class OrderVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public OrderVo(Long id,Long orderNo,Integer personNumbers,BigDecimal payableAmountSum,BigDecimal realAmountSum,BigDecimal reduce,BigDecimal discount,BigDecimal refund,String isRefund,Integer useScore,Long acquireScore,Long buyerId,String buyerMemo,Long cashierId,String cashierName,Long storeId,Long enterpriseId,Long areaId,Long tableId,String tableName,String tradingChannel,String tradingType,String orderState){
        super(id);
        this.orderNo=orderNo;
        this.personNumbers=personNumbers;
        this.payableAmountSum=payableAmountSum;
        this.realAmountSum=realAmountSum;
        this.reduce=reduce;
        this.discount=discount;
        this.refund=refund;
        this.isRefund=isRefund;
        this.useScore=useScore;
        this.acquireScore=acquireScore;
        this.buyerId=buyerId;
        this.buyerMemo=buyerMemo;
        this.cashierId=cashierId;
        this.cashierName=cashierName;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.areaId=areaId;
        this.tableId=tableId;
        this.tableName=tableName;
        this.tradingChannel=tradingChannel;
        this.tradingType=tradingType;
        this.orderState=orderState;
    }

    @ApiModelProperty(value = "业务系统订单号【分表字段】")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderNo;

    @ApiModelProperty(value = "就餐人数")
    private Integer personNumbers;

    @ApiModelProperty(value = "应付总金额")
    private BigDecimal payableAmountSum;

    @ApiModelProperty(value = "实付总金额")
    private BigDecimal realAmountSum;

    @ApiModelProperty(value = "优惠")
    private BigDecimal reduce;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "退款总金额【付款后】")
    private BigDecimal refund;

    @ApiModelProperty(value = "是否有退款：YES，NO")
    private String isRefund;

    @ApiModelProperty(value = "使用积分")
    private Integer useScore;

    @ApiModelProperty(value = "取得积分")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long acquireScore;

    @ApiModelProperty(value = "买单人【挂账人】ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long buyerId;

    @ApiModelProperty(value = "买家备注")
    private String buyerMemo;

    @ApiModelProperty(value = "收银员ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cashierId;

    @ApiModelProperty(value = "收银员名称")
    private String cashierName;

    @ApiModelProperty(value = "门店主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "区域ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long areaId;

    @ApiModelProperty(value = "桌号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tableId;

    @ApiModelProperty(value = "桌台名称")
    private String tableName;

    @ApiModelProperty(value = "支付渠道【支付宝、微信、现金、免单挂账】")
    private String tradingChannel;

    @ApiModelProperty(value = "交易类型【付款、退款、免单、挂账】")
    private String tradingType;

    @ApiModelProperty(value = "订单状态【DFK待付款,FKZ付款中,QXDD取消订单,YJS已结算,MD免单,GZ挂账】")
    private String orderState;

    @ApiModelProperty(value = "已退款金额【付款后】")
    private BigDecimal refunded;

    @ApiModelProperty(value = "操作退款金额")
    private BigDecimal operTionRefund;

    @ApiModelProperty(value = "订单项目[可核算订单项目]")
    private List<OrderItemVo> orderItemVoStatisticsList;

    @ApiModelProperty(value = "订单项目[可核算订单项目总金额]")
    private BigDecimal reducePriceStatistics;

    @ApiModelProperty(value = "订单项目[购物车中订单项目]")
    private List<OrderItemVo> orderItemVoTemporaryList;

    @ApiModelProperty(value = "订单项目[购物车中订单项目总金额]")
    private BigDecimal reducePriceTemporary;

}
