package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：资源树结构体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeItem implements Serializable {

    @ApiModelProperty(value = "节点ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Long id;

    @ApiModelProperty(value = "显示内容")
    public String label;

    @ApiModelProperty(value = "显示内容")
    public Boolean isChecked;

    @ApiModelProperty(value = "显示内容")
    public String systemCode;

    @ApiModelProperty(value = "显示内容")
    public List<TreeItem> children;
}
