package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import com.itheima.restkeeper.validation.Create;
import com.itheima.restkeeper.validation.Delete;
import com.itheima.restkeeper.validation.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @Description：用户表
 */
@Data
@NoArgsConstructor
public class UserVo extends BasicVo  {

    private static final long serialVersionUID = 1L;

    @Builder
    public UserVo(Long id,Long storeId,Long enterpriseId,
                  String username,String realName,
                  String password,String sex,
                  String mobil,String email,
                  BigDecimal discountLimit,BigDecimal reduceLimit,
                  String duties,Integer sortNo){
        super(id);
        this.storeId=storeId;
        this.enterpriseId=enterpriseId;
        this.username=username;
        this.realName=realName;
        this.password=password;
        this.sex=sex;
        this.mobil=mobil;
        this.email=email;
        this.discountLimit=discountLimit;
        this.reduceLimit=reduceLimit;
        this.duties=duties;
        this.sortNo=sortNo;
    }

    @ApiModelProperty(value = "jwtToken令牌")
    private String JwtToken;

    @ApiModelProperty(value = "门店Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(groups = {Create.class, Update.class},message = "请选择门店")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "登录名称")
    @NotBlank(groups = {Create.class, Update.class},message = "登录名为空")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(groups = {Create.class, Update.class},message = "姓名为空")
    private String realName;

    @ApiModelProperty(value = "密码")
    @NotBlank(groups = {Create.class},message = "密码为空")
    private String password;

    @ApiModelProperty(value = "性别")
    @NotBlank(groups = {Create.class, Update.class},message = "性别为空")
    private String sex;

    @ApiModelProperty(value = "电话")
    @NotBlank(groups = {Create.class, Update.class},message = "性别为空")
    private String mobil;

    @ApiModelProperty(value = "邮箱")
    @Email(groups = {Create.class, Update.class},message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "折扣上线")
    @Min(value = 0,groups = {Create.class, Update.class},message = "折扣为负数")
    @Max(value = 10,groups = {Create.class, Update.class},message = "折扣不可超过10")
    private BigDecimal discountLimit;

    @ApiModelProperty(value = "减免金额上线")
    @Min(value = 0,groups = {Create.class, Update.class},message = "减免为负数")
    @Max(value = 100,groups = {Create.class, Update.class},message = "减免不可超过100")
    private BigDecimal reduceLimit;

    @ApiModelProperty(value = "职务")
    private String duties;

    @ApiModelProperty(value = "排序")
    @NotNull(groups = {Create.class, Update.class},message = "排序为空")
    private Integer sortNo;

    @ApiModelProperty(value = "拥有的角色Ids")
    @NotEmpty(groups = {Create.class, Update.class},message = "角色为空")
    private String[] hasRoleIds;

    @ApiModelProperty(value = "选中节点")
    @NotEmpty(groups = {Delete.class},message = "选中节点为空")
    private String[] checkedIds;

    @ApiModelProperty(value = "用户角色")
    private Set<String> roles;

    @ApiModelProperty(value = "用户权限")
    private Set<String> resources;

    @ApiModelProperty(value = "附件信息",dataType = "AffixVo")
    private AffixVo affixVo;

}
