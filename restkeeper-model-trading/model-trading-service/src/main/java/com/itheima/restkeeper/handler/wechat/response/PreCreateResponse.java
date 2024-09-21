package com.itheima.restkeeper.handler.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PreCreateResponse.java
 * @Description TODO
 */
@Data
@NoArgsConstructor
public class PreCreateResponse {

    //请求返回编码
    private String code;

    //二维码请求地址
    @JSONField(name="code_url")
    private String codeUrl;

}
