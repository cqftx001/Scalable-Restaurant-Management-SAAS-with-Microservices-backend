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
@TableName("tab_user_role")
@ApiModel(value="UserRole对象", description="用户角色表")
public class UserRole extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public UserRole(Long id,Long userId,Long roleId){
        super(id);
        this.userId=userId;
        this.roleId=roleId;
    }

    private Long userId;

    private Long roleId;


}
