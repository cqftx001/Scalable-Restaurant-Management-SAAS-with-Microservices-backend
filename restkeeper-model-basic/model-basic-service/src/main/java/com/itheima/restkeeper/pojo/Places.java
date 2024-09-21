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
 * @Description：地方表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_places")
@ApiModel(value="Places对象", description="地方表")
public class Places extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Places(Long id,Long parentId,String cityName){
        super(id);
        this.parentId=parentId;
        this.cityName=cityName;
    }

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String cityName;


}
