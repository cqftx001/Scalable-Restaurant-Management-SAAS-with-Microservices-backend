package com.itheima.restkeeper.handler.wechat.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.PrivateKey;

/**
 * @ClassName WechatPayHttpClient.java
 * @Description 支付宝支付远程调用对象
 */
@Data
@NoArgsConstructor
public class WechatPayHttpClient {

    //商户号
    String mchId;

    //私钥字符串
    String privateKey;

    //商户证书序列号
    String mchSerialNo;

    //V3密钥
    String apiV3Key;

    //请求地址
    String domain;

    @Builder
    public WechatPayHttpClient(String mchId,
                               String privateKey,
                               String mchSerialNo,
                               String apiV3Key,
                               String domain) {
        this.mchId = mchId;
        this.privateKey = privateKey;
        this.mchSerialNo = mchSerialNo;
        this.apiV3Key = apiV3Key;
        this.domain = domain;
    }


    /***
     * @description 构建CloseableHttpClient远程请求对象
     * @return: org.apache.http.impl.client.CloseableHttpClient
     */
    private CloseableHttpClient createHttpClient() throws UnsupportedEncodingException {
        // 加载商户私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, merchantPrivateKey);
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(
                mchId, privateKeySigner);
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                wechatPay2Credentials, apiV3Key.getBytes("utf-8"));
        // 初始化httpClient
        return com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    /***
     * @description 支持post请求的远程调用
     * @param params 携带请求参数
     * @return  返回字符串
     */
    public String doPost(ObjectNode params) throws IOException {
        HttpPost httpPost = new HttpPost("https://"+domain);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(bos, params);

        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        CloseableHttpResponse response = this.createHttpClient().execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }

    /***
     * @description 支持get请求的远程调用
     * @param param 在路径中请求的参数
     * @return
     * @return: 返回字符串
     */
    public String doGet(String param) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder("https://"+domain+param);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = this.createHttpClient().execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }

}
