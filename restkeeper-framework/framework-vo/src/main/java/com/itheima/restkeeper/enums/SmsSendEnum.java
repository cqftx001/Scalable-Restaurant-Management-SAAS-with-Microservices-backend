package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName SmsSend.java
 * @Description TODO
 */
public enum SmsSendEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PEXCEED_THE_LIMIT ("39001", "超过发送上限"),
    SEND_FAIL("39002", "发送短信失败"),
    SEND_SUCCEED("39004", "此短信已送达"),
    QUERY_FAIL("39003", "查询短信发送情况失败"),

    ;

    private String code;
    private String msg;

    SmsSendEnum(String code, String msg) {
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
