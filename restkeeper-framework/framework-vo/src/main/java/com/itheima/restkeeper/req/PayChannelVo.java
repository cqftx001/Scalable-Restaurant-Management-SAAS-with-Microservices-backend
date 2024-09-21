package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PayChannelVo.java
 * @Description 支付通道
 */
@Data
@NoArgsConstructor
public class PayChannelVo extends BasicVo {

    @Builder
    public PayChannelVo(Long id,String channelName,String channelLabel,String domain,String appId,String publicKey,String merchantPrivateKey,String otherConfig,String encryptKey,String remark,Long enterpriseId){
        super(id);
        this.channelName=channelName;
        this.channelLabel=channelLabel;
        this.domain=domain;
        this.appId=appId;
        this.publicKey=publicKey;
        this.merchantPrivateKey=merchantPrivateKey;
        this.otherConfig=otherConfig;
        this.encryptKey=encryptKey;
        this.remark=remark;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "通道名称")
    private String channelName;

    @ApiModelProperty(value = "通道唯一标记")
    private String channelLabel;

    @ApiModelProperty(value = "域名")
    private String domain;

    @ApiModelProperty(value = "商户appid")
    private String appId;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "商户私钥")
    private String merchantPrivateKey;

    @ApiModelProperty(value = "其他配置")
    private String otherConfig;

    @ApiModelProperty(value = "AES混淆密钥")
    private String encryptKey;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "商户ID【系统内部识别使用】")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "扩展配置",dataType = "OtherConfigVo")
    private List<OtherConfigVo> otherConfigs;
}
