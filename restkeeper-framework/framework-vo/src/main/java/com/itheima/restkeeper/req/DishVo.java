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
import java.util.List;

/**
 * @Description：菜品管理
 */
@Data
@NoArgsConstructor
public class DishVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DishVo(Long id,String dishName,Integer dishNumber,String categoryId,
                  BigDecimal price,BigDecimal reducePrice,String code,
                  String description,String dishStatus,Long storeId,Long enterpriseId,Integer sortNo){
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String categoryId;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "菜品优惠价格")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "商品码")
    private String code;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "NO 停售 YES 起售")
    private String dishStatus;

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

    @ApiModelProperty(value = "拥有的口味Id")
    private String[] hasDishFlavor;

    @ApiModelProperty(value = "拥有的口味list")
    private List<DishFlavorVo> dishFlavorVos;

    @ApiModelProperty(value = "数据字典对象")
    private List<DataDictVo> dataDictVos;

    @ApiModelProperty(value = "附件信息",dataType = "AffixVo")
    private AffixVo affixVo;

}
