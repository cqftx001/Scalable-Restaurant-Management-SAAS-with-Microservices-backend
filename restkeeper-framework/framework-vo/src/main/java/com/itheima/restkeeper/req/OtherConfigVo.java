package com.itheima.restkeeper.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName OtherConfig.java
 * @Description 扩展配置
 */
@Data
@NoArgsConstructor
@ApiModel(value="OtherConfigVo对象", description="扩展配置")
public class OtherConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public OtherConfigVo(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    @ApiModelProperty(value = "配置键")
    private String configKey;

    @ApiModelProperty(value = "配置值")
    private String configValue;
}
