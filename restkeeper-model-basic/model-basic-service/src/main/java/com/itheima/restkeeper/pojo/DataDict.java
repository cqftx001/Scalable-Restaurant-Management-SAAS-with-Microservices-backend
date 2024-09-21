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
 * @Description：数据字典表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_data_dict")
@ApiModel(value="DataDict对象", description="数据字典表")
public class DataDict extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DataDict(Long id,String parentKey,String dataKey,String dataValue,String discriptioin){
        super(id);
        this.parentKey=parentKey;
        this.dataKey=dataKey;
        this.dataValue=dataValue;
        this.discriptioin=discriptioin;
    }

    @ApiModelProperty(value = "父key")
    private String parentKey;

    @ApiModelProperty(value = "数据字典KEY")
    private String dataKey;

    @ApiModelProperty(value = "值")
    private String dataValue;

    @ApiModelProperty(value = "描述")
    private String discriptioin;


}
