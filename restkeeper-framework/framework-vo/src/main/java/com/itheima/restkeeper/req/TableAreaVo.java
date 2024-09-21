package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @ClassName TableAreaVo.java
 * @Description 区域Vo
 */
@Data
@NoArgsConstructor
public class TableAreaVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public TableAreaVo(Long id,String areaName,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.areaName=areaName;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "门店主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

}
