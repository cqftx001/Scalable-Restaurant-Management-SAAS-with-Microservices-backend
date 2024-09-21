package com.itheima.restkeeper.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * @Description：
 */
@Data
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@TableName("tab_report_pay")
@ApiModel(value="ReportPay对象", description="")
public class ReportPay extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public ReportPay(Long id,Date payDate,Integer payType,BigDecimal totalAmount,BigDecimal presentAmount,BigDecimal smallAmount,BigDecimal freeAmount,BigDecimal payAmount,Integer personNumbers,Integer payCount,Long storeId,Long enterpriseId){
        super(id);
        this.payDate=payDate;
        this.payType=payType;
        this.totalAmount=totalAmount;
        this.presentAmount=presentAmount;
        this.smallAmount=smallAmount;
        this.freeAmount=freeAmount;
        this.payAmount=payAmount;
        this.personNumbers=personNumbers;
        this.payCount=payCount;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "日期")
    private Date payDate;

    @ApiModelProperty(value = "收款方式")
    private Integer payType;

    @ApiModelProperty(value = "应收金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "赠送金额")
    private BigDecimal presentAmount;

    @ApiModelProperty(value = "抹零金额")
    private BigDecimal smallAmount;

    @ApiModelProperty(value = "免单金额")
    private BigDecimal freeAmount;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "就餐人数")
    private Integer personNumbers;

    @ApiModelProperty(value = "交易单数")
    private Integer payCount;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
