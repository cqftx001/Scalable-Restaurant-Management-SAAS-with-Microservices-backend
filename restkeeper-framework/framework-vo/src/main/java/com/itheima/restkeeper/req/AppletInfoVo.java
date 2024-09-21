package com.itheima.restkeeper.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName AppletInfo.java
 * @Description 小程序门主题显示信息系
 */
@Data
@Builder
@NoArgsConstructor
public class AppletInfoVo implements Serializable {

    @Builder
    public AppletInfoVo(BrandVo brandVo, StoreVo storeVo, TableVo tableVo,List<CategoryVo> categoryVos, List<DishVo> dishVos) {
        this.brandVo = brandVo;
        this.storeVo = storeVo;
        this.tableVo = tableVo;
        this.categoryVos = categoryVos;
        this.dishVos = dishVos;
    }

    @ApiModelProperty(value = "品牌")
    private BrandVo brandVo;

    @ApiModelProperty(value = "门店")
    private StoreVo storeVo;

    @ApiModelProperty(value = "桌台")
    private TableVo tableVo;

    @ApiModelProperty(value = "菜品分类")
    private List<CategoryVo> categoryVos;

    @ApiModelProperty(value = "菜品")
    private List<DishVo> dishVos;

}
