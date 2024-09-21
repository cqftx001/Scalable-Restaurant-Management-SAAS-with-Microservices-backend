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
 * @Description：菜品及套餐分类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_category")
@ApiModel(value="Category对象", description="菜品及套餐分类")
public class Category extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Category(Long id,String categoryType,String categoryName,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.categoryType=categoryType;
        this.categoryName=categoryName;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "类型 菜品：DISH  套餐：MEAL")
    private String categoryType;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;


}
