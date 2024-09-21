package com.itheima.restkeeper.handler.wechat.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itheima.restkeeper.enums.TradingEnum;
import com.itheima.restkeeper.exception.ProjectException;
import com.itheima.restkeeper.handler.wechat.response.PreCreateResponse;
import com.itheima.restkeeper.handler.wechat.response.QueryResponse;
import com.itheima.restkeeper.handler.wechat.response.RefundResponse;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.ExceptionsUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

/**
 * @ClassName WechatPayClient.java
 * @Description 功能列表
 */
@Slf4j
public class WechatPayClient {

    //appId
    String appid;

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

    /***
     * @description 构建客户端
     *
     * @param mchId  商户号
     * @param privateKey 私钥字符串
     * @param mchSerialNo 商户证书序列号
     * @param apiV3Key V3密钥
     * @return
     */
    @Builder
    public WechatPayClient(String appid,
                           String mchId,
                           String privateKey,
                           String mchSerialNo,
                           String apiV3Key,
                           String domain) {
        this.appid = appid;
        this.mchId = mchId;
        this.privateKey = privateKey;
        this.mchSerialNo = mchSerialNo;
        this.apiV3Key = apiV3Key;
        this.domain=domain;
    }

    /***
     * @description 统一收单线下交易预创建
     * @param outTradeNo 发起支付传递的交易单号
     * @param amount 交易金额【元】
     * @param description 商品描述
     * @return
     */
    public PreCreateResponse preCreate(String outTradeNo,String amount,String description) throws ProjectException{
        //请求地址
        String uri ="/v3/pay/transactions/native";
        WechatPayHttpClient httpClient = WechatPayHttpClient.builder()
                .mchId(mchId)
                .mchSerialNo(mchSerialNo)
                .apiV3Key(apiV3Key)
                .privateKey(privateKey)
                .domain(domain+uri)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode bodyParams = objectMapper.createObjectNode();
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(100));
        bodyParams.put("mchid",mchId)
                  .put("appid",appid )
                  .put("description", description)
                  .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
                  .put("out_trade_no", outTradeNo);
        bodyParams.putObject("amount")
                  .put("total", multiply.intValue());
        String body = null;
        try {
            body =  httpClient.doPost(bodyParams);
        } catch (IOException e) {
            log.error("微信支付：preCreate，发生异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.NATIVE_PAY_FAIL);
        }
        PreCreateResponse preCreateResponse = JSONObject.parseObject(body, PreCreateResponse.class);
        preCreateResponse.setCode("200");
        return preCreateResponse;
    }

    /***
     * @description 统一收单线下交易查询
     * @param outTradeNo 发起支付传递的交易单号
     * @return
     */
    public QueryResponse query(String outTradeNo){
        //请求地址
        String uri ="/v3/pay/transactions/out-trade-no";
        WechatPayHttpClient httpClient = WechatPayHttpClient.builder()
                .mchId(mchId)
                .mchSerialNo(mchSerialNo)
                .apiV3Key(apiV3Key)
                .privateKey(privateKey)
                .domain(domain+uri)
                .build();
        String body = null;
        try {
            //uri参数对象
            String uriParams ="/"+outTradeNo+"?mchid="+mchId;
            body =  httpClient.doGet(uriParams);
        } catch (IOException | URISyntaxException e) {
            log.error("微信支付：query，发生异常：{}", ExceptionsUtil.getStackTraceAsString(e));
        }
        QueryResponse queryResponse = JSONObject.parseObject(body, QueryResponse.class);
        queryResponse.setCode("200");
        return queryResponse;
    }

    /***
     * @description 统一收单交易退款接口
     * @param outTradeNo 发起支付传递的交易单号
     * @param amount 退款金额
     * @param outRefundNo 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔
     * @param total 原订单金额
     * @return
     */
    public RefundResponse refund(String outTradeNo,String amount,String outRefundNo,String total) throws ProjectException{
        //请求地址
        String uri ="/v3/refund/domestic/refunds";
        WechatPayHttpClient httpClient = WechatPayHttpClient.builder()
                .mchId(mchId)
                .mchSerialNo(mchSerialNo)
                .apiV3Key(apiV3Key)
                .privateKey(privateKey)
                .domain(domain+uri)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode bodyParams = objectMapper.createObjectNode();
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(100));
        BigDecimal bigDecimalTotal = new BigDecimal(total);
        BigDecimal multiplyTotal = bigDecimalTotal.multiply(new BigDecimal(100));
        bodyParams.put("out_refund_no", outRefundNo)
                  .put("out_trade_no", outTradeNo);
        bodyParams.putObject("amount")
                  .put("refund", multiply.intValue())
                  .put("total", multiplyTotal.intValue())
                  .put("currency", "CNY");
        String body = null;
        try {
            body =  httpClient.doPost(bodyParams);
        } catch (IOException e) {
            log.error("微信支付：refund，发生异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradingEnum.NATIVE_REFUND_FAIL);
        }
        RefundResponse refundResponse = JSONObject.parseObject(body, RefundResponse.class);
        refundResponse.setCode("200");
        return refundResponse;
    }

    /***
     * @description 统一收单交易退款接口查询
     * @param outRefundNo 商户系统内部的退款单号
     * @return
     */
    public RefundResponse queryRefund(String outRefundNo){
        //请求地址
        String uri ="/v3/refund/domestic/refunds";
        WechatPayHttpClient httpClient = WechatPayHttpClient.builder()
                .mchId(mchId)
                .mchSerialNo(mchSerialNo)
                .apiV3Key(apiV3Key)
                .privateKey(privateKey)
                .domain(domain+uri)
                .build();
        String body = null;
        try {
            String uriParams ="/"+outRefundNo;
            body =  httpClient.doGet(uriParams);
        } catch (IOException | URISyntaxException e) {
            log.error("微信支付：queryRefund，发生异常：{}", ExceptionsUtil.getStackTraceAsString(e));
        }
        RefundResponse refundResponse = JSONObject.parseObject(body, RefundResponse.class);
        refundResponse.setCode("200");
        return refundResponse;
    }


}
