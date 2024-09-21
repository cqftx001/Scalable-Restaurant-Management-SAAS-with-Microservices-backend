package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @ClassName StoreVo.java
 * @Description 门店
 */
@Data
@NoArgsConstructor
public class StoreVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public StoreVo(Long id,Long brandId,String storeName,String province,String city,String area,String address,String managerId,Long enterpriseId,Double longitude,Double dimensionality){
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String managerId;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "维度")
    private Double dimensionality;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

}
