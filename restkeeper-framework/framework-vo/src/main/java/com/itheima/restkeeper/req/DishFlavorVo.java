package com.itheima.restkeeper.req;

import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName DishFlavorVo.java
 * @Description 口味信息
 */
@Data
@NoArgsConstructor
public class DishFlavorVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DishFlavorVo(Long id,Long dishId,String dataKey){
        super(id);
        this.dishId=dishId;
        this.dataKey=dataKey;
    }

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "数据字典KEY")
    private String dataKey;
}
