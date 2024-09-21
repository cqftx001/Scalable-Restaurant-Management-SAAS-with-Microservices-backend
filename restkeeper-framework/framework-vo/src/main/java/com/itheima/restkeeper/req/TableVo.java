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
 * @ClassName TableVo.java
 * @Description 桌台Vo
 */
@Data
@NoArgsConstructor
public class TableVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public TableVo(Long id,Long areaId,String tableName,Integer tableSeatNumber,String tableStatus,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.areaId=areaId;
        this.tableName=tableName;
        this.tableSeatNumber=tableSeatNumber;
        this.tableStatus=tableStatus;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "区域ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long areaId;

    @ApiModelProperty(value = "桌台名称")
    private String tableName;

    @ApiModelProperty(value = "桌台座位数目")
    private Integer tableSeatNumber;

    @ApiModelProperty(value = " FREE:空闲 USER:开桌  lOCK 锁桌")
    private String tableStatus;

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
