package com.itheima.restkeeper.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：桌台区域
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_table_area")
@ApiModel(value="TableArea对象", description="桌台区域")
public class TableArea extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public TableArea(Long id,String areaName,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.areaName=areaName;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;


}
