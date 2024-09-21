package com.itheima.restkeeper.req;

import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * @Description：日志模块
 */
@Data
@NoArgsConstructor
public class LogBusinessVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public LogBusinessVo(Long id,String requestId,String host,String hostAddress,String requestUri,String requestMethod,String requesBody,String responseBody,String responseCode,String responseMsg,Long userId,String userName){
        super(id);
        this.requestId=requestId;
        this.host=host;
        this.hostAddress=hostAddress;
        this.requestUri=requestUri;
        this.requestMethod=requestMethod;
        this.requesBody=requesBody;
        this.responseBody=responseBody;
        this.responseCode=responseCode;
        this.responseMsg = responseMsg;
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

    @ApiModelProperty(value = "时间查询")
    private List<String> createdTimeQuerty;

}
