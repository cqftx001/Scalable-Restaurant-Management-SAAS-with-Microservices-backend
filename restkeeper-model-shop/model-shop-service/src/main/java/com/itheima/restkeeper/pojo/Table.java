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
 * @Description：桌台
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_table")
@ApiModel(value="Table对象", description="桌台")
public class Table extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Table(Long id,Long areaId,String tableName,Integer tableSeatNumber,String tableStatus,Long storeId,Long enterpriseId,Integer sortNo){
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
    private Long areaId;

    @ApiModelProperty(value = "桌台名称")
    private String tableName;

    @ApiModelProperty(value = "桌台座位数目")
    private Integer tableSeatNumber;

    @ApiModelProperty(value = "FREE:空闲 USE:开桌  lOCK 锁桌")
    private String tableStatus;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;


}
