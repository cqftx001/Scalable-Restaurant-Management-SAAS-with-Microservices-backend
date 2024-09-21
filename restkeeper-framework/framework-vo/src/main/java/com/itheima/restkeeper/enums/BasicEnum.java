package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName BasicEnum.java
 * @Description 基础枚举
 */
public enum BasicEnum implements IBasicEnum {

    SUCCEED("200","操作成功"),
    SYSYTEM_FAIL("1503","系统运行异常"),
    DUBBO_FAIL("1504","dubbo远程调用异常"),
    VALID_EXCEPTION("1505","参数校验异常")
    ;

    private String code;
    private String msg;

    BasicEnum(String code, String msg) {
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
