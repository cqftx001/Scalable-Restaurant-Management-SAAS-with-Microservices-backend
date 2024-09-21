package com.itheima.restkeeper.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：菜品管理
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_dish")
@ApiModel(value="Dish对象", description="菜品管理")
public class Dish extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Dish(Long id,String dishName,Integer dishNumber,String categoryId,BigDecimal price,BigDecimal reducePrice,String code,String description,String dishStatus,Long storeId,Long enterpriseId,Integer sortNo){
        super(id);
        this.dishName=dishName;
        this.dishNumber=dishNumber;
        this.categoryId=categoryId;
        this.price=price;
        this.reducePrice=reducePrice;
        this.code=code;
        this.description=description;
        this.dishStatus=dishStatus;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    @ApiModelProperty(value = "菜品数量")
    private Integer dishNumber;

    @ApiModelProperty(value = "菜品分类id")
    private String categoryId;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "菜品优惠价格")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "商品码")
    private String code;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "0 停售 1 起售")
    private String dishStatus;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;


}
