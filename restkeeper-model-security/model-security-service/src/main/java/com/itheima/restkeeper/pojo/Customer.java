package com.itheima.restkeeper.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_customer")
@ApiModel(value="Customer对象", description="")
public class Customer extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Customer(Long id,Long storeId,Long enterpriseId,String username,String realName,String password,String sex,String mobil,String email,BigDecimal discountLimit,BigDecimal reduceLimit,String duties,Integer sortNo){
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

    @ApiModelProperty(value = "门店Id")
    private Long storeId;

    @ApiModelProperty(value = "商户号")
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


}
