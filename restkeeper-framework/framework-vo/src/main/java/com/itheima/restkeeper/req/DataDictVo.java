package com.itheima.restkeeper.req;

import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @Description：数据字典表
 */
@Data
@NoArgsConstructor
public class DataDictVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DataDictVo(Long id, String parentKey, String dataKey, String dataValue,
                      String discriptioin, String editType){
        super(id);
        this.parentKey=parentKey;
        this.dataKey=dataKey;
        this.dataValue=dataValue;
        this.discriptioin=discriptioin;
        this.editType=editType;
    }

    @ApiModelProperty(value = "父key")
    private String parentKey;

    @ApiModelProperty(value = "数据字典KEY")
    private String dataKey;

    @ApiModelProperty(value = "值")
    private String dataValue;

    @ApiModelProperty(value = "描述")
    private String discriptioin;

    @ApiModelProperty(value = "是否可编辑")
    private String editType;


}
