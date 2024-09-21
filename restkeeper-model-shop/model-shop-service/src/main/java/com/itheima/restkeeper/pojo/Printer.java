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
 * @Description：打印机表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_printer")
@ApiModel(value="Printer对象", description="打印机表")
public class Printer extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Printer(Long id,String printerName,String machineCode,String hardwareVersion,String softwareVersion,Integer state,Integer areaType,Integer printerNumber,Boolean enableMadeMenu,Boolean enableChangeMenu,Boolean enableChangeTable,Boolean enableReturnDish,Boolean enableBeforehand,Boolean enableBill,Boolean enableCustomer,Long storeId,Long enterpriseId){
        super(id);
        this.printerName=printerName;
        this.machineCode=machineCode;
        this.hardwareVersion=hardwareVersion;
        this.softwareVersion=softwareVersion;
        this.state=state;
        this.areaType=areaType;
        this.printerNumber=printerNumber;
        this.enableMadeMenu=enableMadeMenu;
        this.enableChangeMenu=enableChangeMenu;
        this.enableChangeTable=enableChangeTable;
        this.enableReturnDish=enableReturnDish;
        this.enableBeforehand=enableBeforehand;
        this.enableBill=enableBill;
        this.enableCustomer=enableCustomer;
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "打印机名称")
    private String printerName;

    @ApiModelProperty(value = "打印机终端号")
    private String machineCode;

    @ApiModelProperty(value = "打印机硬件版本号")
    private String hardwareVersion;

    @ApiModelProperty(value = "软件版本号")
    private String softwareVersion;

    @ApiModelProperty(value = "打印机状态")
    private Integer state;

    @ApiModelProperty(value = "打印机所在区域类型，1:后厨打印机；2:收银区打印")
    private Integer areaType;

    @ApiModelProperty(value = "打印份数")
    private Integer printerNumber;

    @ApiModelProperty(value = "是否支持制作菜单")
    private Boolean enableMadeMenu;

    @ApiModelProperty(value = "是否支持转菜单")
    private Boolean enableChangeMenu;

    @ApiModelProperty(value = "是否支持转台单")
    private Boolean enableChangeTable;

    @ApiModelProperty(value = "是否支持退菜打印")
    private Boolean enableReturnDish;

    @ApiModelProperty(value = "是否支持前台预结单")
    private Boolean enableBeforehand;

    @ApiModelProperty(value = "是否支持结账单")
    private Boolean enableBill;

    @ApiModelProperty(value = "是否支持打印客单")
    private Boolean enableCustomer;

    @ApiModelProperty(value = "门店主键id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
