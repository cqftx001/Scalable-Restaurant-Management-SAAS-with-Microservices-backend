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
 * @Description：打印机菜品关联表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_printer_dish")
@ApiModel(value="PrinterDish对象", description="打印机菜品关联表")
public class PrinterDish extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public PrinterDish(Long id,Long dishId,Long printerId,Long storeId,Long enterpriseId){
        super(id);
        this.dishId=dishId;
        this.printerId=printerId;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
    }

    private Long dishId;

    @ApiModelProperty(value = "打印机ID")
    private Long printerId;

    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
