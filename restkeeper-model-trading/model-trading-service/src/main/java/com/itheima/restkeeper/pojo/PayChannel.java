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
@TableName("tab_pay_channel")
@ApiModel(value="PayChannel对象", description="")
public class PayChannel extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public PayChannel(Long id,String channelName,String channelLabel,String domain,String appId,String publicKey,String merchantPrivateKey,String otherConfig,String encryptKey,String remark,Long enterpriseId){
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
    private Long enterpriseId;


}
