package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

/**
 * @ClassName EnterpriseVo.java
 * @Description 企业请求对象
 */
@Data
@NoArgsConstructor
public class EnterpriseVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public EnterpriseVo(Long id, Long enterpriseId, String enterpriseName,
                        String enterpriseNo, String province, String area,Date expireTime,
                        String city, String address, String status, Long proposerId, String webSite,String appWebSite){
        super(id);
        this.enterpriseId=enterpriseId;
        this.enterpriseName=enterpriseName;
        this.enterpriseNo=enterpriseNo;
        this.province=province;
        this.area=area;
        this.city=city;
        this.address=address;
        this.status=status;
        this.expireTime=expireTime;
        this.applicationTime=applicationTime;
        this.webSite=webSite;
        this.appWebSite=appWebSite;
    }

    @ApiModelProperty(value = "商户ID【系统内部识别使用】")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    private Date applicationTime;

    @ApiModelProperty(value = "商户门店web站点")
    private String webSite;

    @ApiModelProperty(value = "商户h5web站点")
    private String appWebSite;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "到期时间 (试用下是默认七天后到期，状态改成停用)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
}
