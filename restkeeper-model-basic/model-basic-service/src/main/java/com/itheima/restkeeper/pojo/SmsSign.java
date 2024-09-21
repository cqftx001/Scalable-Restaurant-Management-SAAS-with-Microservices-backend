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
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_sms_sign")
@ApiModel(value="SmsSign对象", description="")
public class SmsSign extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsSign(Long id,String channelLabel,String signName,String signCode,String signType,String documentType,String international,String signPurpose,String remark,String acceptStatus,String acceptMsg,String auditStatus,String auditMsg,String signNo){
        super(id);
        this.channelLabel=channelLabel;
        this.signName=signName;
        this.signCode=signCode;
        this.signType=signType;
        this.documentType=documentType;
        this.international=international;
        this.signPurpose=signPurpose;
        this.remark=remark;
        this.acceptStatus=acceptStatus;
        this.acceptMsg=acceptMsg;
        this.auditStatus=auditStatus;
        this.auditMsg=auditMsg;
        this.signNo=signNo;
    }

    @ApiModelProperty(value = "通道唯一标识")
    private String channelLabel;

    @ApiModelProperty(value = "签名名称")
    private String signName;

    @ApiModelProperty(value = "三方签名code:发送短信可能用到")
    private String signCode;

    @ApiModelProperty(value = "签名类型")
    private String signType;

    @ApiModelProperty(value = "证明类型")
    private String documentType;

    @ApiModelProperty(value = "是否国际/港澳台短信")
    private String international;

    @ApiModelProperty(value = "签名用途：0：自用。1：他用。")
    private String signPurpose;

    @ApiModelProperty(value = "短信申请说明")
    private String remark;

    @ApiModelProperty(value = "是否受理成功")
    private String acceptStatus;

    @ApiModelProperty(value = "受理返回信息")
    private String acceptMsg;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    @ApiModelProperty(value = "审核信息")
    private String auditMsg;

    @ApiModelProperty(value = "应用签名编号：多签名编号相同则认为是一个签名多个通道公用")
    private String signNo;


}
