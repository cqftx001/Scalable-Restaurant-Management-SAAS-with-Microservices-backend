package com.itheima.restkeeper.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 返回结果
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrap<T> implements Serializable {

    //响应返回编码
    @ApiModelProperty(value = "状态码")
    private String code;

    //响应返回信息
    @ApiModelProperty(value = "状态信息")
    private String msg;

    //返回结果
    @ApiModelProperty(value = "返回结果")
    private T datas;

    //操作用户
    @ApiModelProperty(value = "操作人ID")
    private Long userId;

    //操作用户名称
    @ApiModelProperty(value = "操作人姓名")
    private String userName;

    //创建时间,处理json的时间参数解析
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

}
