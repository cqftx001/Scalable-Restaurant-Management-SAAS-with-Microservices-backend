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
@TableName("tab_sms_channel")
@ApiModel(value="SmsChannel对象", description="")
public class SmsChannel extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsChannel(Long id,String channelName,String channelLabel,String channelType,String domain,String accessKeyId,String accessKeySecret,String otherConfig,String level,String remark){
        super(id);
        this.channelName=channelName;
        this.channelLabel=channelLabel;
        this.channelType=channelType;
        this.domain=domain;
        this.accessKeyId=accessKeyId;
        this.accessKeySecret=accessKeySecret;
        this.otherConfig=otherConfig;
        this.level=level;
        this.remark=remark;
    }

    @ApiModelProperty(value = "通道名称")
    private String channelName;

    @ApiModelProperty(value = "通道唯一标记")
    private String channelLabel;

    @ApiModelProperty(value = "通道类型，1：文字，2：语音，3：推送")
    private String channelType;

    @ApiModelProperty(value = "域名")
    private String domain;

    @ApiModelProperty(value = "秘钥id")
    private String accessKeyId;

    @ApiModelProperty(value = "秘钥值")
    private String accessKeySecret;

    @ApiModelProperty(value = "其他配置")
    private String otherConfig;

    @ApiModelProperty(value = "优先级")
    private String level;

    @ApiModelProperty(value = "说明")
    private String remark;


}
