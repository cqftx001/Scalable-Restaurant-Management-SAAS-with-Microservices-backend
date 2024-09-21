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
 * @Description：黑名单表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_sms_blacklist")
@ApiModel(value="SmsBlacklist对象", description="黑名单表")
public class SmsBlacklist extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsBlacklist(Long id,String mobile){
        super(id);
        this.mobile=mobile;
    }

    @ApiModelProperty(value = "手机号码")
    private String mobile;


}
