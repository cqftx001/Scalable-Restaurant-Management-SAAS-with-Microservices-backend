package com.itheima.restkeeper.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ShowApiUtil.java
 * @Description 万维易源接口调用工具
 */
@Service
@EnableConfigurationProperties(value = ShowApiProperties.class )
public class ShowApiService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ShowApiProperties showApiProperties;

    /***
     * @description 生成二维码
     * @param targetUrl 目标路径
     * @return 二维码图片路径
     */
    public String handlerQRcode(String targetUrl){
        //生成二维码
        String url = "http://route.showapi.com/887-1" +
                "?showapi_appid=" +showApiProperties.getAppid()+
                "&showapi_sign=" +showApiProperties.getSign()+
                "&content=" +targetUrl+
                "&size=8" +
                "&imgExtName=png";
        String showApiSring = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(showApiSring);
        return jsonObject.getJSONObject("showapi_res_body").getString("imgUrl");
    }
}
