package com.itheima.restkeeper.req;

import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description：黑名单表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SmsBlacklist对象", description="黑名单表")
public class SmsBlacklistVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsBlacklistVo(Long id, String mobile){
        super(id);
        this.mobile=mobile;
    }

    @ApiModelProperty(value = "手机号码")
    private String mobile;


}
