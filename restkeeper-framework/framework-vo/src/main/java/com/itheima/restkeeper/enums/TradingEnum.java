package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName TradingEnum.java
 * @Description TODO
 */
public enum TradingEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    CHECK_TRADING_FAIL("43001","交易单校验失败"),
    TRYLOCK_TRADING_FAIL("43002", "交易单加锁失败"),
    PAYING_TRADING_FAIL("43003", "交易单支付失败"),
    TRADING_STATE_SUCCEED("43004", "交易单已完成"),
    TRADING_STATE_PAYING("43005", "交易单交易中"),
    CONFIG_EMPT("43006", "支付配置为空"),
    NATIVE_PAY_FAIL("43007", "统一下单交易失败"),
    REFUND_FAIL("43008", "查询统一下单交易退款失败"),
    SAVE_OR_UPDATE_FAIL("43009", "交易单保存或修改失败"),
    TRADING_TYPE_FAIL("43010", "未定义的交易类型"),
    NATIVE_QUERY_FAIL("43011", "查询统一下单交易失败"),
    NATIVE_REFUND_FAIL("43012", "统一下单退款交易失败"),
    CASH_PAY_FAIL("43013", "现金交易失败"),
    CREDIT_PAY_FAIL("43015", "信用交易失败"),
    ;

    private String code;
    private String msg;

    TradingEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
