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
@TableName("tab_report_dish")
@ApiModel(value="ReportDish对象", description="")
public class ReportDish extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public ReportDish(Long id,Date payDate,String category,String dishName,Integer dishNumber,BigDecimal dishMoney,Long storeId,Long enterpriseId){
        super(id);
        this.payDate=payDate;
        this.category=category;
        this.dishName=dishName;
        this.dishNumber=dishNumber;
        this.dishMoney=dishMoney;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "支付日期")
    private Date payDate;

    @ApiModelProperty(value = "分类名称")
    private String category;

    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    @ApiModelProperty(value = "销售量")
    private Integer dishNumber;

    @ApiModelProperty(value = "销售额")
    private BigDecimal dishMoney;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
