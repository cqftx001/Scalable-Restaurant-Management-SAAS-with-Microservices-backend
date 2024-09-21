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
 * @Description：门店信息账号
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_store")
@ApiModel(value="Store对象", description="门店信息账号")
public class Store extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Store(Long id,Long brandId,String storeName,String province,String city,String area,String address,String managerId,Long enterpriseId,Double longitude,Double dimensionality){
        super(id);
        this.brandId=brandId;
        this.storeName=storeName;
        this.province=province;
        this.city=city;
        this.area=area;
        this.address=address;
        this.managerId=managerId;
        this.enterpriseId=enterpriseId;
        this.longitude=longitude;
        this.dimensionality=dimensionality;
    }

    @ApiModelProperty(value = "品牌")
    private Long brandId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "地址(省)")
    private String province;

    @ApiModelProperty(value = "地址(市)")
    private String city;

    @ApiModelProperty(value = "地址(区)")
    private String area;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "管理员id")
    private String managerId;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "维度")
    private Double dimensionality;


}
