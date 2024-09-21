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
 * @Description：用户角色表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_role")
@ApiModel(value="Role对象", description="用户角色表")
public class Role extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Role(Long id,String roleName,String label,String description,Integer sortNo){
        super(id);
        this.roleName=roleName;
        this.label=label;
        this.description=description;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色标识")
    private String label;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;


}
