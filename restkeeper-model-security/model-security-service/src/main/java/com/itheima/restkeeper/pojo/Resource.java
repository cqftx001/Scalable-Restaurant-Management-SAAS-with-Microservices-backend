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
 * @Description：资源表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_resource")
@ApiModel(value="Resource对象", description="资源表")
public class Resource extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Resource(Long id,Long parentId,String resourceName,String requestPath,String icon,String isLeaf,String resourceType,Integer sortNo,String description,String systemCode,String isSystemRoot,String label){
        super(id);
        this.parentId=parentId;
        this.resourceName=resourceName;
        this.requestPath=requestPath;
        this.icon=icon;
        this.isLeaf=isLeaf;
        this.resourceType=resourceType;
        this.sortNo=sortNo;
        this.description=description;
        this.systemCode=systemCode;
        this.isSystemRoot=isSystemRoot;
        this.label=label;
    }

    @ApiModelProperty(value = "父Id")
    private Long parentId;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源路径")
    private String requestPath;

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

    private String label;


}
