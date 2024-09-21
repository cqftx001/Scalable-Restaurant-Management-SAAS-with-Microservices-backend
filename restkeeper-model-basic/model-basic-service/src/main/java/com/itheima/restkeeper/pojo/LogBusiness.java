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
 * @Description：
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_log_business")
@ApiModel(value="LogBusiness对象", description="")
public class LogBusiness extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public LogBusiness(Long id,String requestId,String host,String hostAddress,String requestUri,String requestMethod,String requesBody,String responseBody,String responseCode,String responseMsg,Long userId,String userName){
        super(id);
        this.requestId=requestId;
        this.host=host;
        this.hostAddress=hostAddress;
        this.requestUri=requestUri;
        this.requestMethod=requestMethod;
        this.requesBody=requesBody;
        this.responseBody=responseBody;
        this.responseCode=responseCode;
        this.responseMsg=responseMsg;
        this.userId=userId;
        this.userName=userName;
    }

    @ApiModelProperty(value = "请求id")
    private String requestId;

    @ApiModelProperty(value = "域名")
    private String host;

    @ApiModelProperty(value = "ip地址")
    private String hostAddress;

    @ApiModelProperty(value = "请求路径")
    private String requestUri;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求body")
    private String requesBody;

    @ApiModelProperty(value = "应答body")
    private String responseBody;

    @ApiModelProperty(value = "应答code")
    private String responseCode;

    @ApiModelProperty(value = "应答msg")
    private String responseMsg;

    @ApiModelProperty(value = "用户")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;


}
