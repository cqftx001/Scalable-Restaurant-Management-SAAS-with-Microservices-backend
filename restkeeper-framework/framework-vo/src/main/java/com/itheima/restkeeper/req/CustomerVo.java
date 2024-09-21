package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @ClassName CustomerVo.java
 * @Description 客户表
 */
@Data
@NoArgsConstructor
public class CustomerVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public CustomerVo(Long id, Long storeId, Long enterpriseId,
                  String username, String realName,
                  String password, String sex,
                  String mobil, String email,
                  BigDecimal discountLimit, BigDecimal reduceLimit,
                  String duties, Integer sortNo){
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
    private Long storeId;

    @ApiModelProperty(value = "商户号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "登录名称")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "电话")
    private String mobil;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "折扣上线")
    private BigDecimal discountLimit;

    @ApiModelProperty(value = "减免金额上线")
    private BigDecimal reduceLimit;

    @ApiModelProperty(value = "职务")
    private String duties;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "拥有的角色Ids")
    private String[] hasRoleIds;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "用户角色")
    private Set<String> roles;

    @ApiModelProperty(value = "用户权限")
    private Set<String> resources;

    @ApiModelProperty(value = "附件信息",dataType = "AffixVo")
    private AffixVo affixVo;

}
