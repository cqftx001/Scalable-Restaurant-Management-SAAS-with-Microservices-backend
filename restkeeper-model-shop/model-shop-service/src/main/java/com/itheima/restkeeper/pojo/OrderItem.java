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
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_order_item")
@ApiModel(value="OrderItem对象", description="")
public class OrderItem extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public OrderItem(Long id,Long productOrderNo,BigDecimal memberDiscount,BigDecimal discountAmount,Long dishId,String dishName,
                     String categoryId,BigDecimal price,BigDecimal reducePrice,Long dishNum,String dishFlavor){
        super(id);
        this.productOrderNo=productOrderNo;
        this.memberDiscount=memberDiscount;
        this.discountAmount=discountAmount;
        this.dishId=dishId;
        this.dishName=dishName;
        this.categoryId=categoryId;
        this.price=price;
        this.reducePrice=reducePrice;
        this.dishNum=dishNum;
        this.dishFlavor = dishFlavor;
    }

    @ApiModelProperty(value = "业务系统订单号【分表字段】")
    private Long productOrderNo;

    @ApiModelProperty(value = "会员折扣")
    private BigDecimal memberDiscount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "菜品ID")
    private Long dishId;

    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    @ApiModelProperty(value = "菜品分类id")
    private String categoryId;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "菜品优惠价格")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "菜品数量")
    private Long dishNum;

    @ApiModelProperty(value = "菜品口味")
    private String dishFlavor;

}
