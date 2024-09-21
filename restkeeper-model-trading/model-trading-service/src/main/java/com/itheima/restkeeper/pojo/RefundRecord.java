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
@TableName("tab_refund_record")
@ApiModel(value="RefundRecord对象", description="")
public class RefundRecord extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public RefundRecord(Long id,Long tradingOrderNo,Long productOrderNo,String refundNo,Long enterpriseId,Long storeId,String tradingChannel,String refundStatus,String refundCode,String refundMsg,String memo,BigDecimal refundAmount){
        super(id);
        this.tradingOrderNo=tradingOrderNo;
        this.productOrderNo=productOrderNo;
        this.refundNo=refundNo;
        this.enterpriseId=enterpriseId;
        this.storeId=storeId;
        this.tradingChannel=tradingChannel;
        this.refundStatus=refundStatus;
        this.refundCode=refundCode;
        this.refundMsg=refundMsg;
        this.memo=memo;
        this.refundAmount=refundAmount;
    }

    @ApiModelProperty(value = "交易系统订单号【对于三方来说：商户订单】")
    private Long tradingOrderNo;

    @ApiModelProperty(value = "业务系统订单号")
    private Long productOrderNo;

    @ApiModelProperty(value = "本次退款订单号")
    private String refundNo;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "退款渠道【支付宝、微信、现金】")
    private String tradingChannel;

    @ApiModelProperty(value = "退款状态【成功：SUCCESS,进行中：SENDING】")
    private String refundStatus;

    @ApiModelProperty(value = "返回编码")
    private String refundCode;

    @ApiModelProperty(value = "返回信息")
    private String refundMsg;

    @ApiModelProperty(value = "备注【订单门店，桌台信息】")
    private String memo;

    @ApiModelProperty(value = "本次退款金额")
    private BigDecimal refundAmount;


}
