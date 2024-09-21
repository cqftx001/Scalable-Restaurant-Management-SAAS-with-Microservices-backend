package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @ClassName OrderItemVo.java
 * @Description 订单项
 */
@Data
@NoArgsConstructor
public class OrderItemVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public OrderItemVo(Long id, Long productOrderNo, BigDecimal memberDiscount, BigDecimal discountAmount,
                       Long dishId, String dishName, String categoryId, BigDecimal price, BigDecimal reducePrice,
                       Long dishNum,String dishFlavor){
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productOrderNo;

    @ApiModelProperty(value = "会员折扣")
    private BigDecimal memberDiscount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "菜品ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dishId;

    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    @ApiModelProperty(value = "菜品分类id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String categoryId;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "菜品优惠价格")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "菜品数量")
    private Long dishNum;

    @ApiModelProperty(value = "菜品附件信息")
    private AffixVo affixVo;

    @ApiModelProperty(value = "菜品口味")
    private String dishFlavor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemVo that = (OrderItemVo) o;
        return dishId.equals(that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishId);
    }
}
