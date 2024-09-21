package com.itheima.restkeeper.enums;

import com.itheima.restkeeper.basic.IBasicEnum;

/**
 * @ClassName SmsChannelEnum.java
 * @Description TODO
 */
public enum SmsChannelEnum implements IBasicEnum {
    SUCCEED("200","操作成功"),
    FAIL("1000","操作失败"),
    PAGE_FAIL("47001", "查询通道列表失败"),
    CREATE_FAIL("47002", "保存通道失败"),
    UPDATE_FAIL("47003", "修改通道失败"),
    DELETE_FAIL("47004", "修改通道失败"),
    SELECT_FAIL("47005", "查询通道失败");

    private String code;
    private String msg;

    SmsChannelEnum(String code, String msg) {
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