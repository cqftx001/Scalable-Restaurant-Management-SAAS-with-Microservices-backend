package com.itheima.restkeeper.req;

import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SmsChannelVo.java
 * @Description TODO
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SmsChannelVo对象", description="渠道单表")
public class SmsChannelVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsChannelVo(Long id,String channelName,String channelLabel,String channelType,String domain,String accessKeyId,String accessKeySecret,String otherConfig,String level,String remark){
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

    @ApiModelProperty(value = "短信申请说明")
    private String remark;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "扩展配置",dataType = "OtherConfigVo")
    private List<OtherConfigVo> otherConfigs;

}
