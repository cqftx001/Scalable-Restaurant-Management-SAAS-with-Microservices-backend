package com.itheima.restkeeper.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_order")
@ApiModel(value="Order对象", description="")
public class Order extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Order(Long id,Long orderNo,Integer personNumbers,BigDecimal payableAmountSum,BigDecimal realAmountSum,BigDecimal reduce,BigDecimal discount,BigDecimal refund,String isRefund,Integer useScore,Long acquireScore,Long buyerId,String buyerMemo,Long cashierId,String cashierName,Long storeId,Long enterpriseId,Long areaId,Long tableId,String tableName,String tradingChannel,String tradingType,String orderState){
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
    private Long acquireScore;

    @ApiModelProperty(value = "买单人【挂账人】ID")
    private Long buyerId;

    @ApiModelProperty(value = "买家备注")
    private String buyerMemo;

    @ApiModelProperty(value = "收银员ID")
    private Long cashierId;

    @ApiModelProperty(value = "收银员名称")
    private String cashierName;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "区域ID")
    private Long areaId;

    @ApiModelProperty(value = "桌号ID")
    private Long tableId;

    @ApiModelProperty(value = "桌台名称")
    private String tableName;

    @ApiModelProperty(value = "支付渠道【支付宝、微信、现金、免单挂账】")
    private String tradingChannel;

    @ApiModelProperty(value = "交易类型【付款、退款、免单、挂账】")
    private String tradingType;

    @ApiModelProperty(value = "订单状态【DFK待付款,FKZ付款中,QXDD取消订单,YJS已结算,MD免单,GZ挂账】")
    private String orderState;


}
