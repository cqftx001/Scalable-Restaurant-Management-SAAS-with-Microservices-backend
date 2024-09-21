package com.itheima.restkeeper.req;

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
 * @Description：用户角色表
 */
@Data
@NoArgsConstructor
public class RoleVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public RoleVo(Long id,String roleName,String label,
                  String description,Integer sortNo){
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

    @ApiModelProperty(value = "拥有资源")
    private String[] hasResourceIds;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

}
