package com.itheima.restkeeper.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：企业账号管理
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_enterprise")
@ApiModel(value="Enterprise对象", description="企业账号管理")
public class Enterprise extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Enterprise(Long id,Long enterpriseId,String enterpriseName,String enterpriseNo,String province,String area,
                      String city,String address,String status,Long proposerId,Date expireTime,String webSite,String appWebSite){
        super(id);
        this.enterpriseId=enterpriseId;
        this.enterpriseName=enterpriseName;
        this.enterpriseNo=enterpriseNo;
        this.province=province;
        this.area=area;
        this.city=city;
        this.address=address;
        this.status=status;
        this.proposerId=proposerId;
        this.expireTime=expireTime;
        this.webSite=webSite;
        this.appWebSite=appWebSite;
    }

    @ApiModelProperty(value = "商户ID【系统内部识别使用】")
    private Long enterpriseId;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "工商号")
    private String enterpriseNo;

    @ApiModelProperty(value = "地址(省)")
    private String province;

    @ApiModelProperty(value = "地址(区)")
    private String area;

    @ApiModelProperty(value = "地址(市)")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "状态(试用：trial，停用：stop，正式:official)")
    private String status;

    @ApiModelProperty(value = "申请人Id")
    @TableField("proposer_Id")
    private Long proposerId;

    @ApiModelProperty(value = "到期时间 (试用下是默认七天后到期，状态改成停用)")
    private Date expireTime;

    @ApiModelProperty(value = "商户门店web站点")
    private String webSite;

    @ApiModelProperty(value = "商户h5web站点")
    private String appWebSite;

}
