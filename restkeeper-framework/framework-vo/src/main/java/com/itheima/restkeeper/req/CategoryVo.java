package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @ClassName CategoryVo.java
 * @Description 菜品分类
 */
@Data
@NoArgsConstructor
public class CategoryVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public CategoryVo(Long id,String categoryType,String categoryName,String enableFlag,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.categoryType=categoryType;
        this.categoryName=categoryName;
        this.enableFlag=enableFlag;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "类型 菜品：DISH  套餐：MEAL ")
    private String categoryType;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "是否有效")
    private String enableFlag;

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
