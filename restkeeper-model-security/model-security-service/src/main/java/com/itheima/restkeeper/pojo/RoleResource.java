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
 * @Description：角色资源表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_role_resource")
@ApiModel(value="RoleResource对象", description="角色资源表")
public class RoleResource extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public RoleResource(Long id,Long roleId,Long resourceId){
        super(id);
        this.roleId=roleId;
        this.resourceId=resourceId;
    }

    private Long roleId;

    private Long resourceId;


}
