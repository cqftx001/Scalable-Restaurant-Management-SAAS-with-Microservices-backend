package com.itheima.restkeeper.constant;

/**
 * @ClassName TardingConstant.java
 * @Description TODO
 */
public class TradingConstant {



    //【阿里云退款返回状态】
    //REFUND_SUCCESS:成功
    public static final String REFUND_SUCCESS= "REFUND_SUCCESS";

    //【阿里云返回付款状态】
    //TRADE_CLOSED:未付款交易超时关闭，或支付完成后全额退款
    public static final String ALI_TRADE_CLOSED ="TRADE_CLOSED";
    //TRADE_SUCCESS:交易支付成功
    public static final String ALI_TRADE_SUCCESS="TRADE_SUCCESS";
    //TRADE_FINISHED:交易结束不可退款
    public static final String ALI_TRADE_FINISHED ="TRADE_FINISHED";


    //【微信退款返回状态】
    //SUCCESS：退款成功
    public static final String WECHAT_REFUND_SUCCESS ="SUCCESS";
    //CLOSED：退款关闭
    public static final String WECHAT_REFUND_CLOSED="CLOSED";
    //PROCESSING：退款处理中
    public static final String WECHAT_REFUND_PROCESSING ="PROCESSING";
    //ABNORMAL：退款异常
    public static final String WECHAT_REFUND_ABNORMAL ="TRADE_CLOSED";

    //【微信返回付款状态】
    //SUCCESS：支付成功
    public static final String WECHAT_TRADE_SUCCESS ="TRADE_SUCCESS";
    //REFUND：转入退款
    public static final String WECHAT_TRADE_REFUND ="TRADE_REFUND";
    //NOTPAY：未支付
    public static final String WECHAT_TRADE_NOTPAY ="TRADE_NOTPAY";
    //CLOSED：已关闭
    public static final String WECHAT_TRADE_CLOSED ="TRADE_CLOSED";
    //REVOKED：已撤销（仅付款码支付会返回）
    public static final String WECHAT_TRADE_REVOKED ="TRADE_REVOKED";
    //USERPAYING：用户支付中（仅付款码支付会返回）
    public static final String WECHAT_TRADE_USERPAYING ="TRADE_USERPAYING";
    //PAYERROR：支付失败（仅付款码支付会返回）
    public static final String WECHAT_TRADE_PAYERROR ="TRADE_PAYERROR";

    //【平台:交易渠道】
    //阿里支付
    public static final String TRADING_CHANNEL_ALI_PAY = "ALI_PAY";
    //微信支付
    public static final String TRADING_CHANNEL_WECHAT_PAY = "WECHAT_PAY";
    //现金
    public static final String TRADING_CHANNEL_CASH_PAY = "CASH_PAY";
    //免单挂账【信用渠道】
    public static final String TRADING_CHANNEL_CREDIT_PAY = "CREDIT_PAY";

    //【平台:交易动作】
    //付款
    public static final String TRADING_TYPE_FK = "FK";
    //退款
    public static final String TRADING_TYPE_TK = "TK";
    //免单
    public static final String TRADING_TYPE_MD = "MD";
    //挂账
    public static final String TRADING_TYPE_GZ = "GZ";

    //【平台:交易单、订单状态】
    //待付款
    public static final String DFK ="DFK";
    //付款中
    public static final String FKZ ="FKZ";
    //已结算
    public static final String YJS ="YJS";
    //取消订单
    public static final String QXDD ="QXDD";
    //免单
    public static final String MD ="MD";
    //挂账
    public static final String GZ ="GZ";

    //【平台：退款状态】
    //失败
    public static final String REFUND_STATUS_FAIL= "FAIL";
    //成功
    public static final String REFUND_STATUS_SUCCESS = "SUCCESS";
    //请求中
    public static final String REFUND_STATUS_SENDING= "SENDING";

}
