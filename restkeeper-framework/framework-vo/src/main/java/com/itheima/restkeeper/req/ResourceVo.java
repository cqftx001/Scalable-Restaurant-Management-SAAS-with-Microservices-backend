package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

/**
 * @Description：资源表
 */
@Data
@NoArgsConstructor
public class ResourceVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public ResourceVo(Long id,Long parentId,String resourceName,
                      String requestPath,String label,String icon,String isLeaf,
                      String resourceType,Integer sortNo,String description,
                      String systemCode,String isSystemRoot){
        super(id);
        this.parentId=parentId;
        this.resourceName=resourceName;
        this.requestPath=requestPath;
        this.label=label;
        this.icon=icon;
        this.isLeaf=isLeaf;
        this.resourceType=resourceType;
        this.sortNo=sortNo;
        this.description=description;
        this.systemCode=systemCode;
        this.isSystemRoot=isSystemRoot;
    }

    @ApiModelProperty(value = "父Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源路径")
    private String requestPath;

    @ApiModelProperty(value = "资源标识")
    private String label;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否叶子节点")
    private String isLeaf;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "系统归属")
    private String systemCode;

    @ApiModelProperty(value = "是否根节点")
    private String isSystemRoot;

    /**
     * 选中节点
     */
    @ApiModelProperty(value = "选择的字段")
    private String[] checkedIds;
}
