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
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_dish_flavor")
@ApiModel(value="DishFlavor对象", description="")
public class DishFlavor extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DishFlavor(Long id,Long dishId,String dataKey){
        super(id);
        this.dishId=dishId;
        this.dataKey=dataKey;
    }

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "数据字典KEY")
    private String dataKey;


}
