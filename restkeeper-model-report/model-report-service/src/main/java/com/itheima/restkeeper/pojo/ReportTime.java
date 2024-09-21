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
@TableName("tab_report_time")
@ApiModel(value="ReportTime对象", description="")
public class ReportTime extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public ReportTime(Long id,Date payDate,String payTime,Integer totalCount,BigDecimal totalAmount,Long storeId,Long enterpriseId){
        super(id);
        this.payDate=payDate;
        this.payTime=payTime;
        this.totalCount=totalCount;
        this.totalAmount=totalAmount;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "日期")
    private Date payDate;

    @ApiModelProperty(value = "时间段")
    private String payTime;

    @ApiModelProperty(value = "单数")
    private Integer totalCount;

    @ApiModelProperty(value = "销售额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
